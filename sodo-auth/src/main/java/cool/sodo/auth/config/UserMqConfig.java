package cool.sodo.auth.config;

import cool.sodo.auth.message.UserMqProperty;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * RabbitMq 配置文件，不用与其他模块一同配置，所以直接注册在容器中
 *
 * @author TimeChaser
 * @date 2020/12/2 6:20 下午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({UserMqProperty.class})
public class UserMqConfig {

    @Resource
    private UserMqProperty userMqProperty;

    @Bean
    public Queue userMqQueue() {
        return new Queue(userMqProperty.getQueueName(), false);
    }

    @Bean
    public TopicExchange userMqExchange() {
        return new TopicExchange(userMqProperty.getExchangeName(), false, false);
    }

    @Bean
    public Binding userMqBinding(Queue userMqQueue, TopicExchange userMqExchange) {
        return BindingBuilder.bind(userMqQueue).to(userMqExchange).with(userMqProperty.getRoutingKey());
    }
}
