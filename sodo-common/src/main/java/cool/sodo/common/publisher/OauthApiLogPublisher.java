package cool.sodo.common.publisher;

import cool.sodo.common.domain.LogApi;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.event.OauthApiLogEvent;
import cool.sodo.common.service.CommonAccessTokenService;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.LogAbstractUtil;
import cool.sodo.common.util.SpringUtil;
import cool.sodo.common.util.StringUtil;
import cool.sodo.common.util.WebUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * LogApi 事件发布者
 * <p>
 * 注册成组件的原因：需要注入数据库的参数
 *
 * @author TimeChaser
 * @date 2021/6/19 14:17
 */
@Component
public class OauthApiLogPublisher {

    @Resource
    private CommonAccessTokenService accessTokenService;
    @Resource
    private CommonUserService userService;
    @Resource
    private ServiceInfo serviceInfo;

    /**
     * OauthApi 日志事件发布者
     *
     * @param request        请求体
     * @param logApi         日志实体
     * @param responseStatus 响应状态
     * @param responseBody   响应体
     * @param time           请求处理器执行时间
     */
    public void publishEvent(HttpServletRequest request,
                             LogApi logApi,
                             Integer responseStatus,
                             String responseBody,
                             Long time) {

        String token = WebUtil.getAccessToken(request);
        if (!StringUtil.isEmpty(token) && accessTokenService.validateAccessToken(token)) {
            logApi.setUserId(userService.getUserIdentityByIdentity(
                    accessTokenService.getAccessTokenCache(token).getIdentity()
            ).getUserId());
        }
        logApi.setTime(StringUtil.isEmpty(time) ? null : Math.toIntExact(time));
        logApi.setResponseStatus(responseStatus);
        logApi.setResponseBody(responseBody);
        logApi.setRequestUrl(WebUtil.getRequestUrl(request, serviceInfo.getPath()));
        LogAbstractUtil.addRequestInfo(logApi, request);

        SpringUtil.publishEvent(new OauthApiLogEvent(this, logApi));
    }
}
