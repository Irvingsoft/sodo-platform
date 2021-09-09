package cool.sodo.common.core.publisher;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.util.SpringUtil;
import cool.sodo.common.core.event.OauthIpCheckEvent;

import java.util.HashMap;

/**
 * OauthIp 校验事件发布者
 *
 * @author TimeChaser
 * @date 2021/6/16 12:15
 */
public class OauthIpCheckPublisher {

    public static void publishEvent(String ipId) {

        HashMap<String, Object> eventSource = new HashMap<>(Constants.HASHMAP_SIZE_DEFAULT);
        eventSource.put(Constants.CHECK_EVENT, ipId);
        SpringUtil.publishEvent(new OauthIpCheckEvent(eventSource));
    }
}
