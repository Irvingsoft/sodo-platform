package cool.sodo.common.core.annotation;

import java.lang.annotation.*;

/**
 * 用于注入请求处理器的当前用户参数
 *
 * @author TimeChaser
 * @date 2021/7/1 11:33
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
