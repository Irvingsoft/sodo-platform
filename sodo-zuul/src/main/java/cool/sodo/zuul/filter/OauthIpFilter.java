package cool.sodo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.util.WebUtil;
import cool.sodo.zuul.service.OauthIpService;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.PatternMatchUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * OauthIp 过滤器
 *
 * @author TimeChaser
 * @date 2021/6/10 17:36
 */
public class OauthIpFilter extends ZuulFilter {

    public static final String ERROR_IP = "IP 被限制！";

    @Resource
    private OauthIpService oauthIpService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        return currentContext.getResponseStatusCode() != ResultEnum.INVALID_CLIENT.getCode();
    }

    /**
     * IP 拦截器
     * <p>
     * 1. 根据 ClientId 查询 IP 白名单，IP 白名单不为空，客户端 IP 存在于 IP 白名单中放行，不存在则拦截
     * 2. IP 白名单为空，查询 IP 黑名单，客户端 IP 存在于 IP 黑名单中拦截，不存在则放行
     * 3. 异步更新 IP 拦截次数
     *
     * @author TimeChaser
     * @date 2021/6/11 10:54
     */
    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (isSkip(request)) {
            return null;
        }

        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        String ip = WebUtil.getIp(request);
        if (!oauthIpService.validateOauthIp(clientId, ip)) {
            currentContext.setResponseStatusCode(ResultEnum.INVALID_IP.getCode());
            throw new SoDoException(ResultEnum.INVALID_IP, ERROR_IP);
        }
        return null;
    }

    private Boolean isSkip(HttpServletRequest request) {
        return PatternMatchUtils.simpleMatch(cool.sodo.zuul.common.Constants.CLIENT_IGNORE, request.getRequestURI());
    }
}
