package cool.sodo.log.starter.event;

import cool.sodo.log.starter.domain.LogApi;
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
