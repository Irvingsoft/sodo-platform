package cool.sodo.redis.starter.aspect;

import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.redis.starter.annotation.Lock;
import cool.sodo.redis.starter.component.LockComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/9 21:09
 */
@Aspect
@Component
public class LockAspect {

    @Resource
    private LockComponent lockComponent;

    @Around(value = "@within(lock) || @annotation(lock)")
    public Object process(ProceedingJoinPoint point, Lock lock) throws Throwable {

        if (StringUtil.isEmpty(lock)) {
            lock = point.getTarget().getClass().getDeclaredAnnotation(Lock.class);
        }
        if (StringUtil.isEmpty(lock)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "分布式锁为空！");
        }
        boolean lockUp = false;
        String lockKey = lockComponent.getKey(lock.key(), point);
        try {
            if (lock.waitTime() > 0) {
                lockUp = lockComponent.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.timeUnit());
            } else {
                lockUp = lockComponent.tryLock(lockKey, lock.leaseTime(), lock.timeUnit());
            }
            if (lockUp) {
                return point.proceed();
            } else {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "锁等待超时！");
            }
        } finally {
            if (lockUp) {
                lockComponent.unlock(lockKey);
            }
        }
    }
}
