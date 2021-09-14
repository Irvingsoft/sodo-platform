package cool.sodo.rabbitmq.starter.config;

import cool.sodo.rabbitmq.starter.producer.LogMqProducer;
import cool.sodo.rabbitmq.starter.property.LogMqProperty;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/8/12 15:22
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({LogMqProperty.class})
public class LogMqConfig {

    @Resource
    private LogMqProperty logMqProperty;

    @Bean
    public LogMqProducer logMqProducer() {
        return new LogMqProducer();
    }

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
