package cool.sodo.common.listener;

import cool.sodo.common.config.LogMqConfig;
import cool.sodo.common.domain.LogBusiness;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Notification;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.event.BusinessLogEvent;
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
public class BusinessLogListener {

    @Resource
    private ServiceInfo serviceInfo;
    @Resource
    private LogMqProducer logMqProducer;
    @Resource
    private LogMqProperty logMqProperty;

    @Async
    @EventListener(BusinessLogEvent.class)
    public void logBusiness(BusinessLogEvent event) {

        HashMap<String, Object> eventSource = (HashMap<String, Object>) event.getSource();
        LogBusiness logBusiness = (LogBusiness) eventSource.get(Constants.LOG_EVENT);
        LogAbstractUtil.addOtherInfo(logBusiness, serviceInfo);

        logMqProducer.sendMessage(new Notification(
                logMqProperty.getBusinessType(),
                serviceInfo.getName(),
                logBusiness));
    }
}
