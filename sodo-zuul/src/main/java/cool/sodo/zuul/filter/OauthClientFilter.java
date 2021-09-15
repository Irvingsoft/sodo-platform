package cool.sodo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.service.CommonOauthClientService;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.PatternMatchUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * OauthClient 过滤器
 *
 * @author TimeChaser
 * @date 2021/6/10 17:37
 */
public class OauthClientFilter extends ZuulFilter {

    public static final String ERROR_CLIENT = "未知的客户端！";

    @Resource
    private CommonOauthClientService oauthClientService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 1. 校验 ClientId 是否合法
     * //     * 2. 根据 ClientId 查询 Api 接口路径列表，进行 路径 和 请求方法的匹配
     * //     * 3. 路径要根据接口方法进行处理，去除参数？或者模式匹配？
     * 如果难以定位到具体的 Api，考虑使用 Controller 层的环绕增强（AOP）
     * StringUtil.startsWithIgnoreCase()
     * <p>
     * OauthClientFilter 和 OauthApiFilter 合并？ OauthClientAndApiFilter
     *
     * @author TimeChaser
     * @date 2021/6/11 10:59
     */
    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (isSkip(request)) {
            return null;
        }

        String clientId = WebUtil.getHeaderNullable(request, Constants.CLIENT_ID);
        if (StringUtil.isEmpty(clientId) || !validateOauthClientInUse(clientId)) {
            currentContext.setResponseStatusCode(ResultEnum.INVALID_CLIENT.getCode());
            throw new SoDoException(ResultEnum.INVALID_CLIENT, ERROR_CLIENT);
        }
        return null;
    }

    private Boolean isSkip(HttpServletRequest request) {
        return PatternMatchUtils.simpleMatch(cool.sodo.zuul.common.Constants.CLIENT_IGNORE, request.getRequestURI());
    }

    private Boolean validateOauthClientInUse(String clientId) {
        return oauthClientService.getOauthClientIdentity(clientId).getInUse();
    }
}
