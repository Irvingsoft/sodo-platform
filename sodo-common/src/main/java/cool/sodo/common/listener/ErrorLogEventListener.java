package cool.sodo.common.listener;

import cool.sodo.common.config.LogMqConfig;
import cool.sodo.common.domain.LogError;
import cool.sodo.common.entity.Notification;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.event.ErrorLogEvent;
import cool.sodo.common.message.LogMqProducer;
import cool.sodo.common.message.LogMqProperty;
import cool.sodo.common.util.LogAbstractUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ErrorLog 事件监听器
 *
 * @author TimeChaser
 * @date 2021/6/18 12:24
 */
@Component
@ConditionalOnBean({LogMqConfig.class})
public class ErrorLogEventListener {

    @Resource
    private ServiceInfo serviceInfo;
    @Resource
    private LogMqProducer logMqProducer;
    @Resource
    private LogMqProperty logMqProperty;

    @Async
    @EventListener(ErrorLogEvent.class)
    public void logError(ErrorLogEvent event) {

        LogError logError = event.getLogError();
        LogAbstractUtil.addOtherInfo(logError, serviceInfo);

        logMqProducer.sendMessage(new Notification(
                logMqProperty.getErrorType(),
                serviceInfo.getName(),
                logError));
    }
}
