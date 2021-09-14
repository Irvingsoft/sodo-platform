package cool.sodo.log.starter.listener;

import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.log.starter.domain.LogBusiness;
import cool.sodo.log.starter.event.BusinessLogEvent;
import cool.sodo.log.starter.util.LogAbstractUtil;
import cool.sodo.rabbitmq.starter.entity.Notification;
import cool.sodo.rabbitmq.starter.producer.LogMqProducer;
import cool.sodo.rabbitmq.starter.property.LogMqProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/14 11:28
 */
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
