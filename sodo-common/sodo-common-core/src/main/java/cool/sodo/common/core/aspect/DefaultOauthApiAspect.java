package cool.sodo.common.core.aspect;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.component.RedisCacheHelper;
import cool.sodo.common.core.domain.AccessToken;
import cool.sodo.common.core.domain.OauthApi;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.core.property.ServiceInfo;
import cool.sodo.common.core.publisher.OauthApiAccessPublisher;
import cool.sodo.common.core.service.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author TimeChaser
 * @date 2021/9/14 22:45
 */
@Aspect
@Component
public class DefaultOauthApiAspect {

    public static final int REQUEST_LIMIT_INIT = 1;

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
    private CommonRoleService roleService;
    @Resource
    private CommonMenuService menuService;

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
        AccessToken accessToken;

        OauthApi oauthApi = oauthApiService.getOauthApiIdentityByPathAndMethod(
                WebUtil.getApiPath(request, method, serviceInfo.getPath()),
                request.getMethod());

        // Check Whether OauthApi is Enabled.
        if (!oauthApi.getInUse()) {
            throw new SoDoException(ResultEnum.UNUSED_API, "API 未启用！");
        }
        // Check Whether the OauthClient Has Permission to Access the OauthApi.
        if (!clientApiService.validateClientApi(clientId, oauthApi.getApiId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "客户端权限不足！");
        }
        // if OauthApi`s Request times in a Period is Limited.
        if (oauthApi.getRequestLimit()) {
            processRequestLimit(request, oauthApi);
        }
        // if OauthApi Needs Auth
        if (oauthApi.getAuth()) {

            String token = WebUtil.getAccessToken(request);
            // AccessToken Check, And User Status Check.
            accessToken = accessTokenService.getFromCache(token);
            checkAccessToken(accessToken, clientId);
            User user = userService.getIdentity(accessToken.getIdentity(), clientId);
            userService.checkUserStatus(user);
            // Check User`s Permission if OauthApi Needs.
            if (!StringUtil.isEmpty(oauthApi.getCode())) {
                checkUserAccess(user.getUserId(), oauthApi.getCode());
            }
        }

        // Record Request Nums
        OauthApiAccessPublisher.publishEvent(oauthApi.getApiId());

        return point.proceed();
    }

    private void checkAccessToken(AccessToken accessToken, String clientId) {

        if (StringUtil.isEmpty(accessToken)) {
            throw new SoDoException(ResultEnum.UNAUTHORIZED, "请登录后重试！");
        }
        if (!accessToken.getClientId().equals(clientId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "令牌不属于当前客户端！");
        }
    }

    private void processRequestLimit(HttpServletRequest request, OauthApi oauthApi) {

        if (StringUtil.isEmpty(oauthApi.getLimitNum()) || StringUtil.isEmpty(oauthApi.getLimitPeriod())) {
            return;
        }
        String requestCacheId = Constants.REQUEST_LIMIT_CACHE_PREFIX + WebUtil.getSessionId(request) + oauthApi.getApiId();
        if (redisCacheHelper.hasKey(requestCacheId)) {

            int requestNum = (int) redisCacheHelper.get(requestCacheId);
            if (requestNum < oauthApi.getLimitNum()) {
                redisCacheHelper.set(requestCacheId, ++requestNum, redisCacheHelper.getExpire(requestCacheId));
            } else {
                throw new SoDoException(ResultEnum.BAD_REQUEST, "超出请求数限制，请稍后重试！");
            }
        } else {
            redisCacheHelper.set(requestCacheId, REQUEST_LIMIT_INIT, Long.valueOf(oauthApi.getLimitPeriod()));
        }
    }

    private void checkUserAccess(String userId, String code) {

        List<String> codeList = menuService.listCode(roleService.listRoleId(userId));
        if (!StringUtil.isEmpty(codeList)
                && !codeList.contains(code)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "权限不足！");
        }
    }
}
