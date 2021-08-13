package cool.sodo.common.annotation;

import java.lang.annotation.*;

/**
 * 用于标记需要记录日志的接口
 *
 * @author TimeChaser
 * @date 2021/7/1 11:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessLogger {

    String businessType() default "";

    String message() default "";
}
