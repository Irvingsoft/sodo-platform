package cool.sodo.log.starter.listener;

import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.common.core.domain.LogApi;
import cool.sodo.log.starter.event.OauthApiLogEvent;
import cool.sodo.log.starter.util.LogAbstractUtil;
import cool.sodo.rabbitmq.starter.entity.Notification;
import cool.sodo.rabbitmq.starter.producer.LogMqProducer;
import cool.sodo.rabbitmq.starter.property.LogMqProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/14 11:31
 */
public class OauthApiLogEventListener {

    @Resource
    private ServiceInfo serviceInfo;
    @Resource
    private LogMqProducer logMqProducer;
    @Resource
    private LogMqProperty logMqProperty;

    @Async
    @EventListener(OauthApiLogEvent.class)
    public void logApi(OauthApiLogEvent event) {

        LogApi logApi = event.getLogApi();
        LogAbstractUtil.addOtherInfo(logApi, serviceInfo);

        logMqProducer.sendMessage(new Notification(
                logMqProperty.getApiType(),
                serviceInfo.getName(),
                logApi));
    }
}
