package cool.sodo.redis.annotation;

import cool.sodo.common.base.entity.Constants;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author TimeChaser
 * @date 2021/9/9 11:02
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Locks.class)
@Documented
public @interface Lock {

    /**
     * 锁的key
     */
    String key();

    /**
     * 获取锁的最大尝试时间(单位 {@code unit})
     * 该值大于0则使用 locker.tryLock 方法加锁，否则使用 locker.lock 方法
     */
    long waitTime() default Constants.LOCK_WAIT_MILLISECONDS;

    /**
     * 加锁的时间(单位 {@code unit})，超过这个时间后锁便自动解锁；
     * 如果leaseTime为-1，则保持锁定直到显式解锁
     */
    long leaseTime() default Constants.LOCK_LEAST_MILLISECONDS;

    /**
     * 参数的时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 是否公平锁
     */
    boolean isFair() default false;
}