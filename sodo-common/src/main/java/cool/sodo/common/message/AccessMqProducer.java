package cool.sodo.common.message;

import cool.sodo.common.config.AccessMqConfig;
import cool.sodo.common.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@ConditionalOnBean({AccessMqConfig.class})
public class AccessMqProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private AccessMqProperty accessMqProperty;

    public void sendMessage(Notification notification) {
        log.info("新日志发生变更通知：[{}]，通告该日志到 RabbitMQ", notification.getEventType());
        rabbitTemplate.convertAndSend(accessMqProperty.getExchangeName(), accessMqProperty.getRoutingKey(), notification);
    }
}
