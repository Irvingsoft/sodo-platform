package cool.sodo.common.listener;

import cool.sodo.common.config.AccessMqConfig;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Notification;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.event.OauthApiAccessEvent;
import cool.sodo.common.message.AccessMqProducer;
import cool.sodo.common.message.AccessMqProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * OauthApi 访问事件监听者
 *
 * @author TimeChaser
 * @date 2021/6/16 11:07
 */
@Component
@ConditionalOnBean({AccessMqConfig.class})
public class OauthApiAccessListener {

    @Resource
    private AccessMqProducer accessMqProducer;
    @Resource
    private AccessMqProperty accessMqProperty;
    @Resource
    private ServiceInfo serviceInfo;

    @Async
    @EventListener(OauthApiAccessEvent.class)
    public void updateOauthApiAccess(OauthApiAccessEvent event) {

        Map<String, Object> eventSource = (Map<String, Object>) event.getSource();
        String apiId = (String) eventSource.get(Constants.ACCESS_EVENT);

        accessMqProducer.sendMessage(new Notification(
                accessMqProperty.getApiType(),
                serviceInfo.getName(),
                apiId));
    }
}
