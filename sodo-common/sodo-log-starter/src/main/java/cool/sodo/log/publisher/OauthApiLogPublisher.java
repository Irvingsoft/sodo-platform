package cool.sodo.log.publisher;

import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.common.base.service.CommonAccessTokenService;
import cool.sodo.common.base.service.CommonUserService;
import cool.sodo.common.base.util.SpringUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.log.domain.LogApi;
import cool.sodo.log.event.OauthApiLogEvent;
import cool.sodo.log.util.LogAbstractUtil;
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

        String token = WebUtil.getAccessTokenNullable(request);
        if (!StringUtil.isEmpty(token)
                && !StringUtil.isEmpty(accessTokenService.getFromCache(token))) {
            logApi.setUserId(userService.getIdentity(
                    accessTokenService.getFromCache(token).getIdentity()
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
