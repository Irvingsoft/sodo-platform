package cool.sodo.common.event;

import cool.sodo.common.domain.LogApi;
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
