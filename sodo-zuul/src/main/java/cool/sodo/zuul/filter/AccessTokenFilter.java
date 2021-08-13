package cool.sodo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.zuul.exception.AuthorizationException;
import cool.sodo.zuul.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token 过滤器
 * <p>
 * 1、当 Client 过滤器抛出错误时，Token 过滤器不再过滤
 * 2、排除免 Token 验证的接口
 * 3、检查 Token 是否为空，为空直接抛错
 * 4、检查 Token 是否有效，以及是否与客户端匹配，无效则抛错
 * <p>
 * HandlerExceptionResolver 解决在 ZuulFilter 中直接抛错不能被错误处理类捕获的情况
 *
 * @author TimeChaser
 * @date 2020/11/4 3:52 下午
 */
@Deprecated
public class AccessTokenFilter extends ZuulFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CLIENT_ID = "Client_id";

    @Resource
    @Qualifier(value = "handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;
    @Resource
    private ProxyRequestHelper proxyRequestHelper;
    @Resource
    private AccessTokenService accessTokenService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {

        return RequestContext.getCurrentContext().getResponseStatusCode() != ResultEnum.INVALID_CLIENT.getCode();
    }

    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();

        String uri = this.proxyRequestHelper.buildZuulRequestURI(request);
        if (PatternMatchUtils.simpleMatch(Constants.UNCOVER_CLIENT, uri) || PatternMatchUtils.simpleMatch(Constants.UNCOVER_AUTH, uri)) {
            return null;
        }

        String token = request.getHeader(AUTHORIZATION);
        String clientId = request.getHeader(CLIENT_ID);

        if (StringUtils.isEmpty(token)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(ResultEnum.UNAUTHORIZED.getCode());
            handlerExceptionResolver.resolveException(request, response, null, new AuthorizationException(ResultEnum.UNAUTHORIZED));
            return null;
        }
        if (!accessTokenService.validateAccessToken(token, clientId)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(ResultEnum.INVALID_TOKEN.getCode());
            handlerExceptionResolver.resolveException(request, response, null, new AuthorizationException(ResultEnum.INVALID_TOKEN));
        }

        return null;
    }
}