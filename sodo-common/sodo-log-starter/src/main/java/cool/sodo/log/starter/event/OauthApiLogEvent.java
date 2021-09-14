package cool.sodo.log.starter.event;

import cool.sodo.common.core.domain.LogApi;
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
