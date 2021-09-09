package cool.sodo.log.event;

import cool.sodo.log.domain.LogApi;
import org.springframework.context.ApplicationEvent;

public class OauthApiLogEvent extends ApplicationEvent {

    private final LogApi logApi;

    public OauthApiLogEvent(Object source, LogApi logApi) {
        super(source);
        this.logApi = logApi;
    }

    public LogApi getLogApi() {
        return logApi;
    }
}
