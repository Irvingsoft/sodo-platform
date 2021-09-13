package cool.sodo.rabbitmq.starter.entity;

import lombok.Data;

@Data
public class MqProperty {

    private String queueName;
    private String exchangeName;
    private String routingKey;
}
