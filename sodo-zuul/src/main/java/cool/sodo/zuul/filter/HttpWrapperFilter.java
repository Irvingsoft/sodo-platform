package cool.sodo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.wrapper.CustomHttpServletRequestWrapper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.io.IOException;

/**
 * 请求包装器拦截器，把 HttpServletRequestWrapper 更换成自定义的包装器
 *
 * @author TimeChaser
 * @date 2021/6/11 10:19
 */
@Deprecated
public class HttpWrapperFilter extends ZuulFilter {

    public static final String ERROR_REQUEST = "更换请求包装器失败！";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        try {
            currentContext.setRequest(new CustomHttpServletRequestWrapper(currentContext.getRequest()));
//            currentContext.setResponse(new CustomHttpServletResponseWrapper(currentContext.getResponse()));
        } catch (IOException e) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_REQUEST);
        }
        return null;
    }
}

