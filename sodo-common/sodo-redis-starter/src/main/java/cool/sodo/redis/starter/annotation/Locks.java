package cool.sodo.redis.starter.annotation;

import java.lang.annotation.*;

/**
 * @author TimeChaser
 * @date 2021/9/9 23:42
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Locks {

    Lock[] value();
}
