package cool.sodo.log.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.sodo.log.common.Constants;
import cool.sodo.log.domain.LogApi;
import cool.sodo.log.domain.LogBusiness;
import cool.sodo.log.domain.LogError;
import cool.sodo.log.service.LogApiService;
import cool.sodo.log.service.LogBusinessService;
import cool.sodo.log.service.LogErrorService;
import cool.sodo.rabbitmq.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听异步日志消息
 *
 * @author TimeChaser
 * @date 2021/8/12 15:34
 */
@Component
@Slf4j
@ConditionalOnProperty(prefix = "log-message", name = "enabled", havingValue = "true")
public class LogMqListener {

    @Resource
    private ObjectMapper mapper;
    @Resource
    private LogApiService logApiService;
    @Resource
    private LogErrorService logErrorService;
    @Resource
    private LogBusinessService logBusinessService;

    @RabbitListener(queues = "${log-message.queue-name}")
    public void listen(Notification notification) throws JsonProcessingException {

        Object data = notification.getData();
        String dataString = mapper.writeValueAsString(data);
        log.info("接收到一条通告信息... ");
        log.info("通告消息类型为：[{}]", notification.getEventType());
        log.info("消息来源：[{}]", notification.getOrigin());

        switch (notification.getEventType()) {
            case Constants.LOG_MQ_EVENT_TYPE_API: {
                LogApi logApi = mapper.readValue(dataString, LogApi.class);
                logApiService.insertLogApiByAsync(logApi);
                log.info("消息实体：[{}]", logApi);
            }
            break;
            case Constants.LOG_MQ_EVENT_TYPE_ERROR: {
                LogError logError = mapper.readValue(dataString, LogError.class);
                logErrorService.insertLogErrorByAsync(logError);
                log.error("消息实体：[{}]", logError);
            }
            break;
            case Constants.LOG_MQ_EVENT_TYPE_BUSINESS: {
                LogBusiness logBusiness = mapper.readValue(dataString, LogBusiness.class);
                logBusinessService.insertLogBusinessByAsync(logBusiness);
                log.info("消息实体：[{}]", logBusiness);
            }
            break;
            default:
                break;
        }
    }
}
