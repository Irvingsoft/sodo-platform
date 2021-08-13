package cool.sodo.common.listener;

import cool.sodo.common.entity.Constants;
import cool.sodo.common.event.OauthApiAccessEvent;
import cool.sodo.common.service.CommonOauthApiService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * OauthApi 访问事件监听者
 *
 * @author TimeChaser
 * @date 2021/6/16 11:07
 */
@SuppressWarnings("all")
@Component
public class OauthApiAccessListener {

    @Resource
    private CommonOauthApiService oauthApiService;

    @Async
    @EventListener(OauthApiAccessEvent.class)
    public void updateOauthApiAccess(OauthApiAccessEvent event) {

        Map<String, Object> eventSource = (Map<String, Object>) event.getSource();
        String apiId = (String) eventSource.get(Constants.ACCESS_EVENT);
        oauthApiService.updateOauthApiAccessByAsync(apiId);
    }
}
