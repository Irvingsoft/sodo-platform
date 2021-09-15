package cool.sodo.goods.resolver;

import cool.sodo.common.core.domain.User;
import cool.sodo.common.starter.domain.Shop;
import cool.sodo.common.starter.resolver.CurrentUserArgumentResolver;
import cool.sodo.goods.annotation.CurrentShop;
import cool.sodo.goods.service.ShopService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/**
 * 当前店铺信息参数注入
 *
 * @author TimeChaser
 * @date 2021/5/29 11:11
 */
@SuppressWarnings("all")
public class CurrentShopArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String ERROR_USER_IN_REVIEW = "店铺审核中";

    @Resource
    private ShopService shopService;
    @Resource
    private CurrentUserArgumentResolver currentUserArgumentResolver;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.getParameterType().isAssignableFrom(Shop.class)
                && methodParameter.hasParameterAnnotation(CurrentShop.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        User user = (User) currentUserArgumentResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);

        assert user != null;
        // TODO
        return null;
        /*if (user.getType().equals(Constants.USER_TYPE_SHOP) && user.getStatus().equals(Constants.USER_STATUS_NORMAL)) {
            return shopService.getShopIdentity(user.getUserId());
        } else if (user.getType().equals(Constants.USER_TYPE_SHOP) && user.getStatus().equals(Constants.USER_STATUS_REVIEW)) {
            throw new GoodsException(ResultEnum.IN_REVIEW, ERROR_USER_IN_REVIEW);
        } else {
            throw new GoodsException(ResultEnum.BAD_REQUEST, Constants.ERROR_LIMITS_AUTHORITY, user.getUserId());
        }*/
    }
}
