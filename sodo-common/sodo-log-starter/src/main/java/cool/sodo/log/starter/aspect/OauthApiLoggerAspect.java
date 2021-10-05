package cool.sodo.log.starter.aspect;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.util.JsonUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.component.RedisCacheHelper;
import cool.sodo.common.core.domain.LogApi;
import cool.sodo.common.core.domain.OauthApi;
import cool.sodo.common.core.property.ServiceInfo;
import cool.sodo.common.core.service.CommonOauthApiService;
import cool.sodo.log.starter.publisher.OauthApiLogPublisher;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
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
@Aspect
@Component
public class OauthApiLoggerAspect {

    @Resource
    private ServiceInfo serviceInfo;
    @Resource
    private CommonOauthApiService oauthApiService;
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

        Method method = ((MethodSignature) point.getSignature()).getMethod();

        OauthApi oauthApi = oauthApiService.getOauthApiIdentityByPathAndMethod(
                WebUtil.getApiPath(request, method, serviceInfo.getPath()),
                request.getMethod());

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

    private LogApi generateLogApi(String requestBody, String apiId, String className, String methodName) {

        LogApi logApi = new LogApi();
        logApi.setRequestBody(requestBody);
        logApi.setApiId(apiId);
        logApi.setClassName(className);
        logApi.setMethodName(methodName);
        return logApi;
    }

    @Async
    void saveLogApi(String requestId, LogApi logApi) {
        redisCacheHelper.set(Constants.REQUEST_CACHE_PREFIX + requestId, logApi, Constants.REQUEST_CACHE_EXPIRE_SECONDS);
    }

    private String getRequestBody(ProceedingJoinPoint point) {

        Object[] args = point.getArgs();
        Stream<Object> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);

        List<Object> argList = stream
                .filter(arg -> !(arg instanceof ServletRequest) && !(arg instanceof ServletResponse))
                .collect(Collectors.toList());

        return JsonUtil.toJsonString(argList);
    }

    private Integer getResponseStatus(Object result) {
        return result instanceof Result ? ((Result) result).getCode() : null;
    }

    private String getResponseBody(Object result) {
        return !StringUtil.isEmpty(result) ? JsonUtil.toJsonString(result) : null;
    }
}
