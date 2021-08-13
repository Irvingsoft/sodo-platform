package cool.sodo.common.entity;

import lombok.Data;

@Data
public class MqProperty {

    private String queueName;
    private String exchangeName;
    private String routingKey;
}
