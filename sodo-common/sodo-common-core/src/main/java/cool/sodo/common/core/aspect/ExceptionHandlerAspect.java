package cool.sodo.common.core.aspect;

import com.alibaba.fastjson.JSON;
import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.log.domain.LogApi;
import cool.sodo.log.publisher.OauthApiLogPublisher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理切面
 * 用于解决需要记录日志的接口在执行过程中抛出错误而不能提交日志事件的问题
 *
 * @author TimeChaser
 * @date 2021/7/17 15:44
 */
@Component
@Aspect
public class ExceptionHandlerAspect {

    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private OauthApiLogPublisher apiLogPublisher;

    @Around(value = "@annotation(exceptionHandler)")
    public Object process(ProceedingJoinPoint point, ExceptionHandler exceptionHandler) throws Throwable {

        HttpServletRequest request = WebUtil.getRequest();
        Object result = point.proceed();
        LogApi logApi = getLogApiCache(WebUtil.getHeaderNullable(request, Constants.REQUEST_ID));
        if (!StringUtil.isEmpty(logApi)) {
            apiLogPublisher.publishEvent(request,
                    logApi,
                    getResponseStatus(result),
                    getResponseBody(result),
                    null);
        }
        return result;
    }

    private LogApi getLogApiCache(String requestId) {
        return redisCacheHelper.hasKey(Constants.REQUEST_CACHE_PREFIX + requestId) ?
                (LogApi) redisCacheHelper.get(Constants.REQUEST_CACHE_PREFIX + requestId)
                : null;
    }

    private Integer getResponseStatus(Object result) {
        return result instanceof Result ? ((Result) result).getCode() : null;
    }

    private String getResponseBody(Object result) {
        return !StringUtil.isEmpty(result) ? JSON.toJSONString(result) : null;
    }
}
