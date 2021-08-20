package cool.sodo.common.resolver;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.AccessToken;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.service.CommonAccessTokenService;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.WebUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/**
 * 当前用户信息参数注入
 *
 * @author TimeChaser
 * @date 2021/5/29 11:12
 */
@SuppressWarnings("all")
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String ERROR_USER_RESOLVER = "用户信息注入失败";

    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private CommonUserService userService;
    @Resource
    private CommonAccessTokenService accessTokenService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(User.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        AccessToken accessToken = null;
        String token = WebUtil.getAccessToken(nativeWebRequest);

        if (redisCacheHelper.hasKey(Constants.ACCESS_TOKEN_CACHE_PREFIX + token)) {
            accessToken = (AccessToken) redisCacheHelper.get(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
        } else {
            accessToken = accessTokenService.getAccessToken(token);
            redisCacheHelper.set(Constants.ACCESS_TOKEN_CACHE_PREFIX + accessToken.getToken(), accessToken, Constants.ACCESS_TOKEN_CACHE_EXPIRE);
        }

        return userService.getUserIdentityByIdentity(accessToken.getIdentity());
    }
}
