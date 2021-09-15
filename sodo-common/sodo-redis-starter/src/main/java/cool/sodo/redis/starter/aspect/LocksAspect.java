package cool.sodo.redis.starter.aspect;

import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.redis.starter.annotation.Lock;
import cool.sodo.redis.starter.annotation.Locks;
import cool.sodo.redis.starter.component.LockComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author TimeChaser
 * @date 2021/9/9 21:09
 */
@Aspect
@Component
public class LocksAspect {

    @Resource
    private LockComponent lockComponent;

    @Around(value = "@within(locks) || @annotation(locks)")
    public Object process(ProceedingJoinPoint point, Locks locks) throws Throwable {

        Lock[] lockList = locks.value();
        ArrayList<Boolean> lockUpList = new ArrayList<>();
        for (Lock lock : lockList) {
            if (StringUtil.isEmpty(lock)) {
                lock = point.getTarget().getClass().getDeclaredAnnotation(Lock.class);
            }
            if (StringUtil.isEmpty(lock)) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "分布式锁为空！");
            }
            boolean lockUp;
            String lockKey = lockComponent.getKey(lock.key(), point);
            if (lock.waitTime() > 0) {
                lockUp = lockComponent.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.timeUnit());
            } else {
                lockUp = lockComponent.tryLock(lockKey, lock.leaseTime(), lock.timeUnit());
            }
            lockUpList.add(lockUp);
        }

        try {
            boolean lockAllUp = true;
            for (Boolean lockUp : lockUpList) {
                lockAllUp = lockAllUp && lockUp;
            }
            if (lockAllUp) {
                return point.proceed();
            } else {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "锁等待超时！");
            }
        } finally {
            for (int i = 0; i < lockUpList.size(); i++) {
                if (lockUpList.get(i)) {
                    lockComponent.unlock(lockComponent.getKey(lockList[i].key(), point));
                }
            }
        }
    }
}
