package cool.sodo.common.core.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

/**
 * OauthApi 访问事件
 *
 * @author TimeChaser
 * @date 2021/6/16 10:51
 */
public class OauthApiAccessEvent extends ApplicationEvent {

    public OauthApiAccessEvent(HashMap<String, Object> source) {
        super(source);
    }
}
