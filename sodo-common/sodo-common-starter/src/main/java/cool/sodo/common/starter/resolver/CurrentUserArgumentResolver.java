package cool.sodo.common.starter.resolver;

import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.domain.AccessToken;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.core.service.CommonAccessTokenService;
import cool.sodo.common.core.service.CommonUserService;
import cool.sodo.common.starter.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
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
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

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
                                  WebDataBinderFactory webDataBinderFactory) {

        String token = WebUtil.getAccessToken(nativeWebRequest);
        AccessToken accessToken = accessTokenService.getFromCache(token);
        if (StringUtil.isEmpty(accessToken)) {
            throw new SoDoException(ResultEnum.UNAUTHORIZED, "请登录后重试！");
        }
        return userService.getIdentity(accessToken.getIdentity(),
                accessToken.getClientId());
    }
}
