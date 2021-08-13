package cool.sodo.common.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class ErrorLogEvent extends ApplicationEvent {

    public ErrorLogEvent(HashMap<String, Object> source) {
        super(source);
    }
}
