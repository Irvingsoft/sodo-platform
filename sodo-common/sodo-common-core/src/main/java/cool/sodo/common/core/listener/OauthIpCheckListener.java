package cool.sodo.common.core.listener;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.common.core.event.OauthIpCheckEvent;
import cool.sodo.rabbitmq.config.AccessMqConfig;
import cool.sodo.rabbitmq.entity.Notification;
import cool.sodo.rabbitmq.producer.AccessMqProducer;
import cool.sodo.rabbitmq.property.AccessMqProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * OauthIp 校验事件监听者
 *
 * @author TimeChaser
 * @date 2021/6/16 11:42
 */
@Component
@ConditionalOnBean({AccessMqConfig.class})
public class OauthIpCheckListener {

    @Resource
    private AccessMqProducer accessMqProducer;
    @Resource
    private AccessMqProperty accessMqProperty;
    @Resource
    private ServiceInfo serviceInfo;

    @Async
    @EventListener(OauthIpCheckEvent.class)
    public void updateOauthIpValidNum(OauthIpCheckEvent event) {

        HashMap<String, Object> eventSource = (HashMap<String, Object>) event.getSource();
        String ipId = (String) eventSource.get(Constants.CHECK_EVENT);

        accessMqProducer.sendMessage(new Notification(
                accessMqProperty.getIpType(),
                serviceInfo.getName(),
                ipId));
    }
}
