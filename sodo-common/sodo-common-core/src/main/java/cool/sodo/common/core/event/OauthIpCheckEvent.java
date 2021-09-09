package cool.sodo.common.core.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

/**
 * OauthApi 校验事件
 *
 * @author TimeChaser
 * @date 2021/6/16 11:35
 */
public class OauthIpCheckEvent extends ApplicationEvent {

    public OauthIpCheckEvent(HashMap<String, Object> source) {
        super(source);
    }
}
