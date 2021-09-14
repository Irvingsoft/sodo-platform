package cool.sodo.common.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author TimeChaser
 * @date 2021/9/14 22:42
 */
public interface OauthApiAspect {

    Object process(ProceedingJoinPoint point) throws Throwable;
}
