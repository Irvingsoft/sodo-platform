package cool.sodo.common.core.listener;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.common.core.event.OauthApiAccessEvent;
import cool.sodo.rabbitmq.starter.config.AccessMqConfig;
import cool.sodo.rabbitmq.starter.entity.Notification;
import cool.sodo.rabbitmq.starter.producer.AccessMqProducer;
import cool.sodo.rabbitmq.starter.property.AccessMqProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * OauthApi 访问事件监听者
 *
 * @author TimeChaser
 * @date 2021/6/16 11:07
 */
@Component
@ConditionalOnBean({AccessMqConfig.class})
public class OauthApiAccessListener {

    @Resource
    private AccessMqProducer accessMqProducer;
    @Resource
    private AccessMqProperty accessMqProperty;
    @Resource
    private ServiceInfo serviceInfo;

    @Async
    @EventListener(OauthApiAccessEvent.class)
    public void updateOauthApiAccess(OauthApiAccessEvent event) {

        Map<String, Object> eventSource = (Map<String, Object>) event.getSource();
        String apiId = (String) eventSource.get(Constants.ACCESS_EVENT);

        accessMqProducer.sendMessage(new Notification(
                accessMqProperty.getApiType(),
                serviceInfo.getName(),
                apiId));
    }
}
