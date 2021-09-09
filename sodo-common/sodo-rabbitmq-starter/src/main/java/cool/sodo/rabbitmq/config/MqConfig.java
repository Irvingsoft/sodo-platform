package cool.sodo.rabbitmq.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 RabbitMq 连接池消息转换器
 *
 * @author TimeChaser
 * @date 2021/8/12 13:44
 */
@Configuration
public class MqConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory factory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(factory);
        // 当消息有异常内容时（例如类型不匹配），将不再重新放入队列，直接丢弃
        simpleRabbitListenerContainerFactory.setDefaultRequeueRejected(false);
        // 设置消息转为json
        simpleRabbitListenerContainerFactory.setMessageConverter(messageConverter());
        return simpleRabbitListenerContainerFactory;
    }
}
