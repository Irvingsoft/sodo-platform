package cool.sodo.common.listener;

import cool.sodo.common.config.LogMqConfig;
import cool.sodo.common.domain.LogApi;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Notification;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.event.OauthApiLogEvent;
import cool.sodo.common.message.LogMqProducer;
import cool.sodo.common.message.LogMqProperty;
import cool.sodo.common.util.LogAbstractUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
@ConditionalOnBean({LogMqConfig.class})
public class OauthApiLogListener {

    @Resource
    private ServiceInfo serviceInfo;
    @Resource
    private LogMqProducer logMqProducer;
    @Resource
    private LogMqProperty logMqProperty;

    @Async
    @EventListener(OauthApiLogEvent.class)
    public void logApi(OauthApiLogEvent event) {

        HashMap<String, Object> eventSource = (HashMap<String, Object>) event.getSource();
        LogApi logApi = (LogApi) eventSource.get(Constants.LOG_EVENT);
        LogAbstractUtil.addOtherInfo(logApi, serviceInfo);

        logMqProducer.sendMessage(new Notification(
                logMqProperty.getApiType(),
                serviceInfo.getName(),
                logApi));
    }
}
