package cool.sodo.goods.annotation;

import java.lang.annotation.*;

/**
 * 注入当前商铺参数
 *
 * @author TimeChaser
 * @date 2021/5/29 11:06
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentShop {
}
