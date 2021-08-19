package cool.sodo.common.event;

import cool.sodo.common.domain.LogError;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class ErrorLogEvent extends ApplicationEvent {

    private final LogError logError;

    public ErrorLogEvent(Object source, LogError logError) {
        super(source);
        this.logError = logError;
    }

    public LogError getLogError() {
        return logError;
    }
}
