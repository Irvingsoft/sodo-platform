package cool.sodo.log.annotation;

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

    /**
     * 业务类型
     * <p>
     * INSERT/DELETE/UPDATE
     *
     * @author TimeChaser
     * @date 2021/9/9 16:15
     */
    String businessType() default "";

    /**
     * 业务消息
     *
     * @author TimeChaser
     * @date 2021/9/9 16:16
     */
    String message() default "";
}
