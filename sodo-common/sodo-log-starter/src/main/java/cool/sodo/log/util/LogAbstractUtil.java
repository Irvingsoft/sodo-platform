package cool.sodo.log.util;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ServiceInfo;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.log.entity.LogAbstract;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * LogAbstract（日志基类） 工具类
 *
 * @author TimeChaser
 * @date 2021/6/17 22:29
 */
public class LogAbstractUtil {

    /**
     * 补充日志请求信息
     *
     * @author TimeChaser
     * @date 2021/6/17 22:29
     */
    public static void addRequestInfo(LogAbstract logAbstract, HttpServletRequest request) {

        if (!StringUtil.isEmpty(request)) {
            UserAgent userAgent = UserAgent.parseUserAgentString(WebUtil.getHeader(request, Constants.USER_AGENT));

            logAbstract.setClientId(WebUtil.getHeaderNullable(request, Constants.CLIENT_ID));
            logAbstract.setRequestId(WebUtil.getHeaderNullable(request, Constants.REQUEST_ID));
            logAbstract.setUserIp(WebUtil.getIp(request));
            logAbstract.setSystemName(userAgent.getOperatingSystem().getName());
            logAbstract.setBrowserName(userAgent.getBrowser().getName());
            logAbstract.setRequestMethod(request.getMethod());
        }
    }

    /**
     * 补充日志服务信息
     *
     * @param logAbstract 日志实体
     * @param serviceInfo 服务信息实体
     */
    public static void addOtherInfo(LogAbstract logAbstract, ServiceInfo serviceInfo) {

        logAbstract.setServiceId(serviceInfo.getName());
        logAbstract.setServiceIp(serviceInfo.getIpColonPort());
        logAbstract.setServiceHost(serviceInfo.getHostName());
        logAbstract.setEnv(serviceInfo.getEnv());
    }
}
