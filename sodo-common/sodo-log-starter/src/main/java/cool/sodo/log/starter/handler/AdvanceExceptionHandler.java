package cool.sodo.log.starter.handler;

import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.JsonUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.log.starter.publisher.ErrorLogPublisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/12 21:55
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AdvanceExceptionHandler {

    @Resource
    private ErrorLogPublisher errorLogPublisher;

    @ExceptionHandler(value = SoDoException.class)
    public ResponseEntity<Result> handleSoDoException(SoDoException e) {

        errorLogPublisher.publishEvent(
                WebUtil.getContentCachingRequest(),
                e, e.getParams() == null ? null : JsonUtil.toJsonString(e.getParams()));

        if (e.getResultEnum() != null) {
            return new ResponseEntity<>(
                    Result.of(e.getResultEnum(), e.getDetail()),
                    new HttpHeaders(),
                    HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(
                    Result.of(ResultEnum.SERVER_ERROR, e.getDetail()),
                    new HttpHeaders(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {

        errorLogPublisher.publishEvent(WebUtil.getContentCachingRequest(), e, null);
        return Result.of(ResultEnum.SERVER_ERROR, e.getMessage());
    }
}
