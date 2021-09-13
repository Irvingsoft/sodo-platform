package cool.sodo.log.starter.publisher;

import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.common.base.service.CommonAccessTokenService;
import cool.sodo.common.base.service.CommonUserService;
import cool.sodo.common.base.util.SpringUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.log.starter.domain.LogBusiness;
import cool.sodo.log.starter.event.BusinessLogEvent;
import cool.sodo.log.starter.util.LogAbstractUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 业务日志事件发布者
 *
 * @author TimeChaser
 * @date 2021/7/2 19:31
 */
@Component
public class BusinessLogPublisher {

    @Resource
    private CommonAccessTokenService accessTokenService;
    @Resource
    private CommonUserService userService;
    @Resource
    private ServiceInfo serviceInfo;

    /**
     * 发布业务日志事件
     *
     * @param businessType 业务类型
     * @param businessId   业务 ID
     * @param businessData 业务数据
     * @param message      业务备注
     */
    public void publishEvent(HttpServletRequest request, String businessType, String businessId,
                             String businessData, String message, String className, String methodName) {

        LogBusiness logBusiness = new LogBusiness();
        String token = WebUtil.getAccessTokenNullable(request);
        if (!StringUtil.isEmpty(token)
                && !StringUtil.isEmpty(accessTokenService.getFromCache(token))) {
            logBusiness.setUserId(userService.getIdentity(
                    accessTokenService.getFromCache(token).getIdentity()
            ).getUserId());
        }
        logBusiness.setBusinessType(businessType);
        logBusiness.setBusinessId(businessId);
        logBusiness.setBusinessData(businessData);
        logBusiness.setMessage(message);
        logBusiness.setClassName(className);
        logBusiness.setMethodName(methodName);
        logBusiness.setRequestUrl(WebUtil.getRequestUrl(request, serviceInfo.getPath()));
        LogAbstractUtil.addRequestInfo(logBusiness, request);

        SpringUtil.publishEvent(new BusinessLogEvent(this, logBusiness));
    }
}
