package cool.sodo.rabbitmq.starter.producer;

import cool.sodo.rabbitmq.starter.entity.Notification;
import cool.sodo.rabbitmq.starter.property.LogMqProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日志消息生产者
 *
 * @author TimeChaser
 * @date 2021/8/12 15:23
 */
@Slf4j
@Component
public class LogMqProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private LogMqProperty logMqProperty;

    @Async
    public void sendMessage(Notification notification) {
        log.info("新日志发生变更通知：[{}]，通告该日志到 RabbitMQ", notification.getEventType());
        rabbitTemplate.convertAndSend(logMqProperty.getExchangeName(), logMqProperty.getRoutingKey(), notification);
    }
}
