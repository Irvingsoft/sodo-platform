package cool.sodo.log.starter.aspect;

import cool.sodo.common.base.util.WebUtil;
import cool.sodo.log.starter.annotation.BusinessLogger;
import cool.sodo.log.starter.publisher.BusinessLogPublisher;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * BusinessLogger 注解切面
 *
 * @author TimeChaser
 * @date 2021/7/2 19:21
 */
@Component
@Aspect
public class BusinessLoggerAspect {

    @Resource
    private BusinessLogPublisher businessLogPublisher;

    @Around(value = "@annotation(businessLogger)")
    public Object process(ProceedingJoinPoint point, BusinessLogger businessLogger) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = point.getArgs();

        String businessId = null;
        String businessData = null;

        for (Parameter parameter : method.getParameters()) {

            if (parameter.isAnnotationPresent(PathVariable.class)) {
                businessId = args[ArrayUtils.indexOf(parameterNames, parameter.getName())].toString();
            }
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                businessData = args[ArrayUtils.indexOf(parameterNames, parameter.getName())].toString();
            }
        }

        businessLogPublisher.publishEvent(
                WebUtil.getContentCachingRequest(),
                businessLogger.businessType(),
                businessId,
                businessData,
                businessLogger.message(),
                point.getTarget().getClass().getName(),
                method.getName());
        return point.proceed();
    }
}
