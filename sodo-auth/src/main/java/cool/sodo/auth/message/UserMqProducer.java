package cool.sodo.auth.message;

import cool.sodo.rabbitmq.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户消息生产者
 *
 * @author TimeChaser
 * @date 2021/8/12 11:10
 */
@Component
@Slf4j
public class UserMqProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserMqProperty userMqProperty;

    @Async
    public void sendMessage(Notification notification) {
        log.info("新用户发生变更通知：[{}]，通告该用户到 RabbitMQ", notification.getEventType());
        rabbitTemplate.convertAndSend(userMqProperty.getExchangeName(), userMqProperty.getRoutingKey(), notification);
    }
}
