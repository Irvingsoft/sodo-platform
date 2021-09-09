package cool.sodo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.util.UUIDUtil;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * 请求追踪过滤器 给请求设置请求 ID
 *
 * @author TimeChaser
 * @date 2021/7/17 10:17
 */

public class HttpTraceFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.addZuulRequestHeader(Constants.REQUEST_ID, generateRequestId());
        return null;
    }

    private String generateRequestId() {
        return System.currentTimeMillis() + UUIDUtil.generate();
    }
}
