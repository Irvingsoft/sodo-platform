package cool.sodo.common.core.resolver;

import cool.sodo.common.base.domain.AccessToken;
import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.service.CommonAccessTokenService;
import cool.sodo.common.base.service.CommonUserService;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 当前用户信息参数注入
 *
 * @author TimeChaser
 * @date 2021/5/29 11:12
 */
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
                WebUtil.getHeader(nativeWebRequest.getNativeRequest(HttpServletRequest.class), Constants.CLIENT_ID));
    }
}
