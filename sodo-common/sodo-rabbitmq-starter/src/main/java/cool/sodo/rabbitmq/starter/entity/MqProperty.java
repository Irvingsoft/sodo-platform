package cool.sodo.rabbitmq.starter.entity;

import lombok.Data;

/**
 * @author TimeChaser
 * @date 2021/9/14 10:47
 */
@Data
public class MqProperty {

    private String queueName;
    private String exchangeName;
    private String routingKey;
}
