package cool.sodo.zuul.handler;

import com.netflix.zuul.exception.ZuulException;
import cool.sodo.common.entity.Result;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.zuul.exception.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException e) {
        return Result.of(e.getResultEnum());
    }

    @ExceptionHandler(value = ZuulException.class)
    public Result handleZuulException(ZuulException e) {
        return Result.of(ResultEnum.SERVER_ERROR, e.getMessage());
    }
}
// TODO 服务发现失败的异常处理
