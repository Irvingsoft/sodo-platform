package cool.sodo.goods.annotation;


import java.lang.annotation.*;

@Deprecated
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateShop {

    Class<?> objectClass();
}
