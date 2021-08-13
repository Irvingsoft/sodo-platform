package cool.sodo.common.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class OauthApiLogEvent extends ApplicationEvent {

    public OauthApiLogEvent(HashMap<String, Object> source) {
        super(source);
    }
}
