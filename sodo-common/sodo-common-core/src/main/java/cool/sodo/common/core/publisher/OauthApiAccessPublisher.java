package cool.sodo.common.core.publisher;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.util.SpringUtil;
import cool.sodo.common.core.event.OauthApiAccessEvent;

import java.util.HashMap;

/**
 * OauthApi 访问事件发布者
 *
 * @author TimeChaser
 * @date 2021/6/16 11:07
 */
public class OauthApiAccessPublisher {

    /**
     * OauthApi 访问事件发布者
     *
     * @param apiId OauthApi.apiId
     */
    public static void publishEvent(String apiId) {

        HashMap<String, Object> eventSource = new HashMap<>(Constants.HASHMAP_SIZE_DEFAULT);
        eventSource.put(Constants.ACCESS_EVENT, apiId);
        SpringUtil.publishEvent(new OauthApiAccessEvent(eventSource));
    }
}
