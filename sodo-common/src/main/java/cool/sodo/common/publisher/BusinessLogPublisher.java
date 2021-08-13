package cool.sodo.common.publisher;

import cool.sodo.common.domain.LogBusiness;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ServiceInfo;
import cool.sodo.common.event.BusinessLogEvent;
import cool.sodo.common.service.CommonAccessTokenService;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.LogAbstractUtil;
import cool.sodo.common.util.SpringUtil;
import cool.sodo.common.util.WebUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
    public void publishEvent(HttpServletRequest request, String businessType, String businessId, String businessData, String message) {

        LogBusiness logBusiness = new LogBusiness();
        if (!StringUtils.isEmpty(WebUtil.getHeaderNullable(request, Constants.AUTHORIZATION))) {
            logBusiness.setUserId(userService.getUserIdentityByIdentity(
                    accessTokenService.getAccessTokenCache(WebUtil.getHeaderNullable(request, Constants.AUTHORIZATION))
                            .getIdentity()
            ).getUserId());
        }
        logBusiness.setBusinessType(businessType);
        logBusiness.setBusinessId(businessId);
        logBusiness.setBusinessData(businessData);
        logBusiness.setMessage(message);
        logBusiness.setRequestUrl(WebUtil.getRequestUrl(request, serviceInfo.getPath()));
        LogAbstractUtil.addRequestInfo(logBusiness, request);

        HashMap<String, Object> eventSource = new HashMap<>(Constants.HASHMAP_SIZE_DEFAULT);
        eventSource.put(Constants.LOG_EVENT, logBusiness);
        SpringUtil.publishEvent(new BusinessLogEvent(eventSource));
    }
}
