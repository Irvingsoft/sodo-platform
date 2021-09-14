package cool.sodo.log.starter.event;

import cool.sodo.log.starter.domain.LogError;
import org.springframework.context.ApplicationEvent;

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
