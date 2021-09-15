package cool.sodo.log.starter.event;

import cool.sodo.common.core.domain.LogError;
import org.springframework.context.ApplicationEvent;

/**
 * @author TimeChaser
 * @date 2021/9/15 19:06
 */
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
