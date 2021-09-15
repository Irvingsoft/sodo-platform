package cool.sodo.goods.interceptor;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.util.JsonUtil;
import cool.sodo.common.starter.domain.Goods;
import cool.sodo.common.starter.domain.GoodsChoice;
import cool.sodo.common.starter.domain.GoodsSet;
import cool.sodo.common.starter.domain.ShopMenu;
import cool.sodo.goods.annotation.ValidateShop;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.service.GoodsService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author TimeChaser
 * @date 2021/9/14 16:55
 */
@Deprecated
@Component
public class ValidateShopInterceptor implements HandlerInterceptor {

    // 此处应该为 AOP 实现

    public static final String ERROR_ANNOTATION = "注解使用错误";

    @Resource
    private GoodsService goodsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        ValidateShop annotation = method.getAnnotation(ValidateShop.class);

        if (!Objects.isNull(annotation)) {

            Parameter[] parameters = method.getParameters();
            Class<?> objectClass = annotation.objectClass();

            if (objectClass.equals(Goods.class)) {
                Goods goods = JsonUtil.toObject(parameters[0].toString(), Goods.class);
                String shopId = parameters[1].toString();

                if (!goods.getShopId().equals(shopId)) {
                    throw new GoodsException(ResultEnum.BAD_REQUEST, Constants.ERROR_LIMITS_AUTHORITY, shopId);
                }
            } else if (objectClass.equals(ShopMenu.class)) {
                ShopMenu shopMenu = JsonUtil.toObject(parameters[0].toString(), ShopMenu.class);
                String shopId = parameters[1].toString();

                if (!shopMenu.getShopId().equals(shopId)) {
                    throw new GoodsException(ResultEnum.BAD_REQUEST, Constants.ERROR_LIMITS_AUTHORITY, shopId);
                }
            } else if (objectClass.equals(GoodsSet.class)) {
                GoodsSet goodsSet = JsonUtil.toObject(parameters[0].toString(), GoodsSet.class);
                String shopId = parameters[1].toString();

                if (!goodsSet.getShopId().equals(shopId)) {
                    throw new GoodsException(ResultEnum.BAD_REQUEST, Constants.ERROR_LIMITS_AUTHORITY, shopId);
                }
            } else if (objectClass.equals(GoodsChoice.class)) {
                GoodsChoice goodsChoice = JsonUtil.toObject(parameters[0].toString(), GoodsChoice.class);
                String shopId = parameters[1].toString();

                if (!goodsChoice.getShopId().equals(shopId)) {
                    throw new GoodsException(ResultEnum.BAD_REQUEST, Constants.ERROR_LIMITS_AUTHORITY, shopId);
                }
            } else {
                throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_ANNOTATION);
            }
        }

        return true;
    }
}