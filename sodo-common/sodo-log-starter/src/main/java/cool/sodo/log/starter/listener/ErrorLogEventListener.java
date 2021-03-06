package cool.sodo.log.starter.listener;

import cool.sodo.common.core.domain.LogError;
import cool.sodo.common.core.property.ServiceInfo;
import cool.sodo.log.starter.event.ErrorLogEvent;
import cool.sodo.log.starter.util.LogAbstractUtil;
import cool.sodo.rabbitmq.starter.entity.Notification;
import cool.sodo.rabbitmq.starter.producer.LogMqProducer;
import cool.sodo.rabbitmq.starter.property.LogMqProperty;
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
