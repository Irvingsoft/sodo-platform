package cool.sodo.common.core.handler;

import cool.sodo.common.base.util.JsonUtil;
import cool.sodo.log.starter.publisher.ErrorLogPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 异步任务异常处理器
 *
 * @author TimeChaser
 * @date 2021/6/17 22:05
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Resource
    private ErrorLogPublisher errorLogPublisher;

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {

        log.error("Async execution error on method: " + method.toString() +
                ", with message: " + throwable.getMessage() +
                ", with parameters: " + JsonUtil.toJsonString(objects));
        errorLogPublisher.publishEvent(null, throwable, JsonUtil.toJsonString(objects));
    }
}
