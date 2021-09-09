package cool.sodo.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 异常过滤器，用于捕获在其它 Filter 中抛出的异常
 * <p>
 * 关闭了 ZUUL 的转发异常抛出
 * <p>
 * errorLogPublisher.publishEvent(currentContext.getRequest(), (Throwable) e, null);
 *
 * @author TimeChaser
 * @date 2021/7/18 13:49
 */
public class ExceptionFilter extends ZuulFilter {

    public static final String THROWABLE = "throwable";
    public static final String CONTENT_TYPE = "text/json";

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().containsKey(THROWABLE);
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Object e = currentContext.get(THROWABLE);

        if (e instanceof ZuulException) {

            // Remove error code to prevent further error handling in follow-up filters
            currentContext.remove(THROWABLE);
            SoDoException soDoException;

            if (((ZuulException) e).getCause() instanceof SoDoException) {
                soDoException = (SoDoException) ((ZuulException) e).getCause();
            } else {
                soDoException = new SoDoException(ResultEnum.SERVER_ERROR, ((ZuulException) e).getMessage());
            }
            HttpServletResponse response = currentContext.getResponse();

            currentContext.setSendZuulResponse(true);
            currentContext.setResponseBody(JSON.toJSONString(new cool.sodo.zuul.exception.ZuulException(soDoException)));
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}

