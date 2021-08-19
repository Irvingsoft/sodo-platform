package cool.sodo.common.event;

import cool.sodo.common.domain.LogBusiness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

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