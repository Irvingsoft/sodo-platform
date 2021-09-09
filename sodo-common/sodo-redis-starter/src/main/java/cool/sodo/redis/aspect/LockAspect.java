package cool.sodo.redis.aspect;

import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringPool;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.redis.annotation.Lock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author TimeChaser
 * @date 2021/9/9 21:09
 */
@Component
@Aspect
public class LockAspect {

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
    @Resource
    private RedisCacheHelper redisCacheHelper;

    @Around(value = "@within(lock) || @annotation(lock)")
    public Object process(ProceedingJoinPoint point, Lock lock) throws Throwable {

        if (StringUtil.isEmpty(lock)) {
            lock = point.getTarget().getClass().getDeclaredAnnotation(Lock.class);
        }
        if (StringUtil.isEmpty(lock)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "分布式锁为空！");
        }
        String lockKey = lock.key();
        if (StringUtil.isEmpty(lockKey)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "分布式锁的 Key 为空！");
        }
        if (lockKey.contains(StringPool.HASH)) {
            lockKey = getValueBySpEl(point, lockKey);
            if (StringUtil.isEmpty(lockKey)) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "分布式锁的 Key 为空！");
            }
        }

        Boolean lockUp = false;
        try {
            if (lock.waitTime() > 0) {
                lockUp = tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.timeUnit());
            } else {
                lockUp = tryLock(lockKey, lock.leaseTime(), lock.timeUnit());
            }
            if (lockUp) {
                return point.proceed();
            } else {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "锁等待超时！");
            }
        } finally {
            if (lockUp) {
                unlock(lockKey);
            }
        }
    }

    private String getValueBySpEl(ProceedingJoinPoint point, String spEl) {

        String[] parameterNames = nameDiscoverer.getParameterNames(((MethodSignature) point.getSignature()).getMethod());
        Object[] args = point.getArgs();
        if (!StringUtil.isEmpty(parameterNames)) {
            Expression expression = spelExpressionParser.parseExpression(spEl);
            StandardEvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < args.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
            return expression.getValue(context, String.class);
        }
        return null;
    }

    private Boolean tryLock(String key, Long waitTime, Long leastTime, TimeUnit timeUnit) {

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime > timeUnit.toMillis(waitTime)) {
            if (redisCacheHelper.setIfAbsent(Constants.LOCK_PREFIX + key, key, leastTime, timeUnit)) {
                return true;
            }
        }
        return false;
    }

    private Boolean tryLock(String key, Long leastTime, TimeUnit timeUnit) {
        return redisCacheHelper.setIfAbsent(Constants.LOCK_PREFIX + key, key, leastTime, timeUnit);
    }

    private void unlock(String key) {
        redisCacheHelper.delete(Constants.LOCK_PREFIX + key);
    }
}
