package cool.sodo.log.starter.event;

import cool.sodo.common.core.domain.LogApi;
import org.springframework.context.ApplicationEvent;

/**
 * @author TimeChaser
 * @date 2021/9/15 21:35
 */
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
