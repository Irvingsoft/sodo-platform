package cool.sodo.log.starter.event;

import cool.sodo.log.starter.domain.LogBusiness;
import org.springframework.context.ApplicationEvent;

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