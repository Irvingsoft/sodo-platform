package cool.sodo.user.message;

import cool.sodo.common.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class UserMqProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserMqProperty userMqProperty;

    public void sendMessage(Notification notification) {
        log.info("新用户发生变更通知：[{}]，通告该用户到RabbitMQ", notification.getEventType());
        rabbitTemplate.convertAndSend(userMqProperty.getExchangeName(), userMqProperty.getRoutingKey(), notification);
    }
}
