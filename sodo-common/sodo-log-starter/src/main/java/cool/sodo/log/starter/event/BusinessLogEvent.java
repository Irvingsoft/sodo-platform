package cool.sodo.log.starter.event;

import cool.sodo.common.core.domain.LogBusiness;
import org.springframework.context.ApplicationEvent;

/**
 * @author TimeChaser
 * @date 2021/9/15 21:35
 */
public class BusinessLogEvent extends ApplicationEvent {

    private final LogBusiness logBusiness;

    public BusinessLogEvent(Object source, LogBusiness logBusiness) {
        super(source);
        this.logBusiness = logBusiness;
    }

    public LogBusiness getLogBusiness() {
        return logBusiness;
    }
}