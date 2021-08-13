package cool.sodo.common.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class BusinessLogEvent extends ApplicationEvent {

    public BusinessLogEvent(HashMap<String, Object> source) {
        super(source);
    }
}