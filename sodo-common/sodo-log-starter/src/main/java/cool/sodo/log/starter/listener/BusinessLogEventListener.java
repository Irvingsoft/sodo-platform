package cool.sodo.log.starter.listener;

import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.log.starter.domain.LogBusiness;
import cool.sodo.log.starter.event.BusinessLogEvent;
import cool.sodo.log.starter.util.LogAbstractUtil;
import cool.sodo.rabbitmq.starter.config.LogMqConfig;
import cool.sodo.rabbitmq.starter.entity.Notification;
import cool.sodo.rabbitmq.starter.producer.LogMqProducer;
import cool.sodo.rabbitmq.starter.property.LogMqProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ConditionalOnBean({LogMqConfig.class})
public class BusinessLogEventListener {

    @Resource
    private ServiceInfo serviceInfo;
    @Resource
    private LogMqProducer logMqProducer;
    @Resource
    private LogMqProperty logMqProperty;

    @Async
    @EventListener(BusinessLogEvent.class)
    public void logBusiness(BusinessLogEvent event) {

        LogBusiness logBusiness = event.getLogBusiness();
        LogAbstractUtil.addOtherInfo(logBusiness, serviceInfo);

        logMqProducer.sendMessage(new Notification(
                logMqProperty.getBusinessType(),
                serviceInfo.getName(),
                logBusiness));
    }
}
