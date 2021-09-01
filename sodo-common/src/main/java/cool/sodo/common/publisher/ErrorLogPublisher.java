package cool.sodo.common.publisher;

import cool.sodo.common.domain.LogError;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.event.ErrorLogEvent;
import cool.sodo.common.service.CommonAccessTokenService;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * LogError 事件发布者
 *
 * @author TimeChaser
 * @date 2021/6/19 14:17
 */
@Component
public class ErrorLogPublisher {

    @Resource
    private CommonAccessTokenService accessTokenService;
    @Resource
    private CommonUserService userService;
    @Resource
    private ServiceInfo serviceInfo;

    /**
     * 错误日志发布者
     *
     * @param error  异常实体
     * @param params 方法调用参数
     */
    public void publishEvent(HttpServletRequest request, Throwable error, String params) {

        LogError logError = new LogError();

        if (!StringUtil.isEmpty(error)) {
            logError.setStackTrace(ExceptionUtil.getStackTraceAsString(error));
            logError.setExceptionName(error.getClass().getName());
            logError.setMessage(error.getMessage());
            logError.setParams(params);

            StackTraceElement[] elements = error.getStackTrace();
            if (!StringUtil.isEmpty(elements)) {
                StackTraceElement element = elements[0];
                logError.setClassName(element.getClassName());
                logError.setMethodName(element.getMethodName());
                logError.setFileName(element.getFileName());
                logError.setLineNum(element.getLineNumber());
            }
        }

        if (!StringUtil.isEmpty(request)) {
            String token = WebUtil.getAccessTokenNullable(request);
            if (!StringUtil.isEmpty(token)
                    && !StringUtil.isEmpty(accessTokenService.getFromCache(token))) {
                logError.setUserId(userService.getIdentity(
                        accessTokenService.getFromCache(token).getIdentity()
                ).getUserId());
            }
            logError.setRequestUrl(WebUtil.getRequestUrl(request, serviceInfo.getPath()));
            LogAbstractUtil.addRequestInfo(logError, request);
        }

        SpringUtil.publishEvent(new ErrorLogEvent(this, logError));
    }
}
