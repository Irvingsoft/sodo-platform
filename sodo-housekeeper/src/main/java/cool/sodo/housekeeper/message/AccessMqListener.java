package cool.sodo.housekeeper.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.sodo.housekeeper.common.Constants;
import cool.sodo.housekeeper.service.OauthApiService;
import cool.sodo.housekeeper.service.OauthIpService;
import cool.sodo.rabbitmq.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听异步访问消息
 *
 * @author TimeChaser
 * @date 2021/8/13 20:07
 */
@Component
@Slf4j
@ConditionalOnProperty(prefix = "access-message", name = "enabled", havingValue = "true")
public class AccessMqListener {

    @Resource
    private ObjectMapper mapper;
    @Resource
    private OauthApiService oauthApiService;
    @Resource
    private OauthIpService oauthIpService;

    @RabbitListener(queues = "${access-message.queue-name}")
    public void listen(Notification notification) throws JsonProcessingException {

        Object data = notification.getData();
        String dataString = mapper.writeValueAsString(data);
        log.info("接收到一条通告信息... ");
        log.info("通告消息类型为：[{}]", notification.getEventType());
        log.info("消息来源：[{}]", notification.getOrigin());
        log.info("消息实体：[{}]", dataString);

        switch (notification.getEventType()) {
            case Constants.ACCESS_MQ_EVENT_TYPE_API:
                oauthApiService.updateOauthApiAccessByAsync(mapper.readValue(dataString, String.class));
                break;
            case Constants.ACCESS_MQ_EVENT_TYPE_IP:
                oauthIpService.updateOauthIpValidNumByAsync(mapper.readValue(dataString, String.class));
                break;
            default:
                break;
        }
    }
}
