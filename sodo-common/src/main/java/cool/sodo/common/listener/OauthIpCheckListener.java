package cool.sodo.common.listener;

import cool.sodo.common.entity.Constants;
import cool.sodo.common.event.OauthIpCheckEvent;
import cool.sodo.common.service.CommonOauthIpService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * OauthIp 校验事件监听者
 *
 * @author TimeChaser
 * @date 2021/6/16 11:42
 */
@Component
@SuppressWarnings("all")
public class OauthIpCheckListener {

    @Resource
    private CommonOauthIpService oauthIpService;

    @Async
    @EventListener(OauthIpCheckEvent.class)
    public void updateOauthIpValidNum(OauthIpCheckEvent event) {

        HashMap<String, Object> eventSource = (HashMap<String, Object>) event.getSource();
        String ipId = (String) eventSource.get(Constants.CHECK_EVENT);

        oauthIpService.updateOauthIpValidNumByAsync(ipId);
    }
}
