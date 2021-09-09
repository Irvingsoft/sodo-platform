package cool.sodo.rabbitmq.config;

import cool.sodo.rabbitmq.property.AccessMqProperty;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnBean({AccessMqProperty.class})
public class AccessMqConfig {

    @Resource
    private AccessMqProperty accessMqProperty;

    @Bean
    public Queue accessMqQueue() {
        return new Queue(accessMqProperty.getQueueName(), false);
    }

    @Bean
    public TopicExchange accessMqExchange() {
        return new TopicExchange(accessMqProperty.getExchangeName(), false, false);
    }

    @Bean
    public Binding accessMqBinding(Queue accessMqQueue, TopicExchange accessMqExchange) {
        return BindingBuilder.bind(accessMqQueue).to(accessMqExchange).with(accessMqProperty.getRoutingKey());
    }
}
