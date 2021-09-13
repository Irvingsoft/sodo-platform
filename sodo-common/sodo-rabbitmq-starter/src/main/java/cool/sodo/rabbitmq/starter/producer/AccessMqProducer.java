package cool.sodo.rabbitmq.starter.producer;

import cool.sodo.rabbitmq.starter.config.AccessMqConfig;
import cool.sodo.rabbitmq.starter.entity.Notification;
import cool.sodo.rabbitmq.starter.property.AccessMqProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void sendMessage(Notification notification) {
        log.info("新访问发生变更通知：[{}]，通告该访问到 RabbitMQ", notification.getEventType());
        rabbitTemplate.convertAndSend(accessMqProperty.getExchangeName(), accessMqProperty.getRoutingKey(), notification);
    }
}
