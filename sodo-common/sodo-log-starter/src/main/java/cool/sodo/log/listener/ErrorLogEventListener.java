package cool.sodo.log.listener;

import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.log.domain.LogError;
import cool.sodo.log.event.ErrorLogEvent;
import cool.sodo.log.util.LogAbstractUtil;
import cool.sodo.rabbitmq.config.LogMqConfig;
import cool.sodo.rabbitmq.entity.Notification;
import cool.sodo.rabbitmq.producer.LogMqProducer;
import cool.sodo.rabbitmq.property.LogMqProperty;
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
