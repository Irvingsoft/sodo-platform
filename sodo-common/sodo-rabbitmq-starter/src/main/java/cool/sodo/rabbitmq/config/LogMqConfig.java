package cool.sodo.rabbitmq.config;

import cool.sodo.rabbitmq.property.LogMqProperty;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/8/12 15:22
 */
@Configuration
@ConditionalOnBean({LogMqProperty.class})
public class LogMqConfig {

    @Resource
    private LogMqProperty logMqProperty;

    @Bean
    public Queue logMqQueue() {
        return new Queue(logMqProperty.getQueueName(), false);
    }

    @Bean
    public TopicExchange logMqExchange() {
        return new TopicExchange(logMqProperty.getExchangeName(), false, false);
    }

    @Bean
    public Binding logMqBinding(Queue logMqQueue, TopicExchange logMqExchange) {
        return BindingBuilder.bind(logMqQueue).to(logMqExchange).with(logMqProperty.getRoutingKey());
    }
}
