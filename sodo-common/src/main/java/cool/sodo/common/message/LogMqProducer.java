package cool.sodo.common.message;

import cool.sodo.common.config.LogMqConfig;
import cool.sodo.common.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日志消息生产者
 *
 * @author TimeChaser
 * @date 2021/8/12 15:23
 */
@Component
@Slf4j
@ConditionalOnBean({LogMqConfig.class})
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
