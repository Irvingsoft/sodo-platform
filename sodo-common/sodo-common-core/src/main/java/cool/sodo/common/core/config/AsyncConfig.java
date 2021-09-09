package cool.sodo.common.core.config;

import cool.sodo.common.core.handler.AsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

/**
 * 异步任务配置类
 * <p>
 * 将默认的异步任务异常处理器更换成自定义异步任务异常处理器
 *
 * @author TimeChaser
 * @date 2021/6/17 22:04
 */
@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Resource
    private AsyncExceptionHandler asyncExceptionHandler;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }
}
