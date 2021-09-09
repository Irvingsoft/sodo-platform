package cool.sodo.goods.handler;

import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.goods.exception.GoodsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(GoodsException.class)
    public Result handleGoodsException(GoodsException e) {
        if (e.getResultEnum() != null) {
            return Result.of(e.getResultEnum(), e.getDetail());
        } else {
            return Result.of(ResultEnum.SERVER_ERROR, e.getMessage());
        }
    }
}
