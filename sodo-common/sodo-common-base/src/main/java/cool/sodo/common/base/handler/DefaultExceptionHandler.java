package cool.sodo.common.base.handler;

import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 全局异常处理
 * <p>
 * 此处的 ExceptionHandler 不需要指定优先级，
 * org.springframework.web.method.annotation.ExceptionHandlerMethodResolver#getMappedMethod(java.lang.Class)
 * 方法找到可以匹配异常的所有 ExceptionHandler，然后进行-排序-，取深度最小，匹配度最高的 ExceptionHandler
 *
 * @author TimeChaser
 * @date 2021/6/19 14:33
 */
@RestControllerAdvice
@Order()
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(value = SoDoException.class)
    public ResponseEntity<Result> handleSoDoException(SoDoException e) {

        log.error("SoDoException Code: {} Detail: {}", e.getResultEnum().getCode(), e.getDetail());
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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder detail = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            detail.append(fieldError.getDefaultMessage());
            detail.append(" ");
        }
        return Result.of(ResultEnum.BAD_REQUEST, detail.toString());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.of(ResultEnum.INVALID_METHOD, e.getSupportedMethods());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Result handleNoHandlerFoundException(NoHandlerFoundException e) {
        return Result.of(ResultEnum.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> handleException(Exception e) {

        log.error("Exception message: {}", e.getMessage());
        return new ResponseEntity<>(
                Result.of(ResultEnum.SERVER_ERROR, e.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
