package cool.sodo.common.aspect;

import com.alibaba.fastjson.JSON;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.AccessToken;
import cool.sodo.common.domain.LogApi;
import cool.sodo.common.domain.OauthApi;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Result;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.publisher.OauthApiAccessPublisher;
import cool.sodo.common.publisher.OauthApiLogPublisher;
import cool.sodo.common.service.CommonAccessTokenService;
import cool.sodo.common.service.CommonClientApiService;
import cool.sodo.common.service.CommonOauthApiService;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.StringPool;
import cool.sodo.common.util.StringUtil;
import cool.sodo.common.util.WebUtil;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * OauthApi AOP
 *
 * @author TimeChaser
 * @date 2021/6/14 20:22
 */
@Component
@Aspect
public class OauthApiAspect {

    public static final String ERROR_REQUEST = "解析请求失败！";
    public static final String ERROR_API_UNUSED = "API 未启用！";
    public static final String ERROR_CLIENT_API = "客户端权限不足！";
    public static final String ERROR_REQUEST_LIMIT = "超出请求数限制，请稍后重试！";
    public static final String ERROR_AUTHORIZATION = "请登录后重试！";

    @Resource
    private ServiceInfo serviceInfo;
    @Resource
    private CommonOauthApiService oauthApiService;
    @Resource
    private CommonClientApiService clientApiService;
    @Resource
    private CommonUserService userService;
    @Resource
    private CommonAccessTokenService accessTokenService;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private OauthApiLogPublisher apiLogPublisher;


    /**
     * 在所有 Controller 包中的所有类的所有方法上环绕
     *
     * @author TimeChaser
     * @date 2021/6/15 15:40
     */
    @Around("execution(* cool.sodo.*.controller..*(..))")
    public Object process(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = WebUtil.getRequest();

        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        AccessToken accessToken = null;

        OauthApi oauthApi = oauthApiService.getOauthApiIdentityByPathAndMethod(
                getApiPath(request, method),
                request.getMethod());

        // Check Whether OauthApi is Enable.
        if (!oauthApi.getInUse()) {
            throw new SoDoException(ResultEnum.UNUSED_API, ERROR_API_UNUSED);
        }
        // Check Whether the OauthClient Has Permission to Access the OauthApi.
        if (!clientApiService.validateClientApi(clientId, oauthApi.getApiId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_CLIENT_API);
        }
        // if OauthApi`s Request times in a Period is Limited.
        if (oauthApi.getRequestLimit()) {
            processRequestLimit(request, oauthApi);
        }
        // if OauthApi Needs Auth
        if (oauthApi.getAuth()) {

            String token = WebUtil.getAccessToken(request);
            if (StringUtil.isEmpty(token)) {
                throw new SoDoException(ResultEnum.UNAUTHORIZED, ERROR_AUTHORIZATION);
            }
            // AccessToken Check, And User Status Check.
            accessToken = accessTokenService.getAccessTokenCache(token);
            accessTokenService.checkAccessToken(accessToken, clientId);
            userService.checkUserStatus(accessToken.getIdentity());
        }

        // Record Request Nums
        OauthApiAccessPublisher.publishEvent(oauthApi.getApiId());

        // if OauthApi Needs Log.
        if (oauthApi.getLog()) {

            long startTime = System.currentTimeMillis();

            LogApi logApi = generateLogApi(getRequestBody(point),
                    oauthApi.getApiId(),
                    point.getTarget().getClass().getName(),
                    method.getName());
            saveLogApi(WebUtil.getHeader(request, Constants.REQUEST_ID), logApi);

            Object result = point.proceed();

            apiLogPublisher.publishEvent(request,
                    logApi,
                    getResponseStatus(result),
                    getResponseBody(result),
                    System.currentTimeMillis() - startTime);
            return result;
        } else {
            return point.proceed();
        }

    }

    private String getApiPath(HttpServletRequest request, Method method) {

        StringBuilder pathBuilder = new StringBuilder(serviceInfo.getPath() + request.getRequestURI());

        int pathParameterCount = 0;
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                pathParameterCount++;
            }
        }
        for (int i = 0; i < pathParameterCount; i++) {
            pathBuilder = new StringBuilder(pathBuilder.substring(0, pathBuilder.lastIndexOf(StringPool.SLASH)));
        }
        return pathBuilder.toString();
    }

    private LogApi generateLogApi(String requestBody, String apiId, String className, String methodName) {

        LogApi logApi = new LogApi();
        logApi.setRequestBody(requestBody);
        logApi.setApiId(apiId);
        logApi.setClassName(className);
        logApi.setMethodName(methodName);
        return logApi;
    }

    private void saveLogApi(String requestId, LogApi logApi) {
        redisCacheHelper.set(Constants.REQUEST_CACHE_PREFIX + requestId, logApi, Constants.REQUEST_CACHE_EXPIRE);
    }

    private void processRequestLimit(HttpServletRequest request, OauthApi oauthApi) {

        if (StringUtil.isEmpty(oauthApi.getLimitNum()) || StringUtil.isEmpty(oauthApi.getLimitPeriod())) {
            return;
        }
        String requestCacheId = Constants.REQUEST_LIMIT_CACHE_PREFIX + WebUtil.getSessionId(request) + oauthApi.getApiId();
        if (redisCacheHelper.hasKey(requestCacheId)) {

            int requestNum = (int) redisCacheHelper.get(requestCacheId);
            if (requestNum < oauthApi.getLimitNum()) {
                redisCacheHelper.set(requestCacheId, requestNum + 1, redisCacheHelper.getExpire(requestCacheId));
            } else {
                throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_REQUEST_LIMIT);
            }
        } else {
            redisCacheHelper.set(requestCacheId, 1, Long.valueOf(oauthApi.getLimitPeriod()));
        }
    }

    private String getRequestBody(ProceedingJoinPoint point) {

        Object[] args = point.getArgs();
        Stream<Object> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);

        List<Object> argList = stream
                .filter(arg -> !(arg instanceof ServletRequest) && !(arg instanceof ServletResponse))
                .collect(Collectors.toList());

        return JSON.toJSONString(argList);
    }

    private Integer getResponseStatus(Object result) {
        return result instanceof Result ? ((Result) result).getCode() : null;
    }

    private String getResponseBody(Object result) {
        return !StringUtil.isEmpty(result) ? JSON.toJSONString(result) : null;
    }
}
