package cool.sodo.redis.starter.component;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringPool;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.component.RedisCacheHelper;
import org.aspectj.lang.ProceedingJoinPoint;
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
 * @date 2021/9/10 11:24
 */
@Component
public class LockComponent {

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Resource
    private RedisCacheHelper redisCacheHelper;

    public String getKey(String key, ProceedingJoinPoint point) {
        if (StringUtil.isEmpty(key)) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "分布式锁的 Key 为空！");
        }
        if (key.contains(StringPool.HASH)) {
            key = getValueBySpEl(key, point);
            if (StringUtil.isEmpty(key)) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "分布式锁的 Key 为空！");
            }
        }
        return key;
    }

    public String getValueBySpEl(String spEl, ProceedingJoinPoint point) {

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

    public Boolean tryLock(String key, Long waitTime, Long leastTime, TimeUnit timeUnit) {

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeUnit.toMillis(waitTime)) {
            if (redisCacheHelper.setIfAbsent(Constants.LOCK_PREFIX + key, key, leastTime, timeUnit)) {
                return true;
            }
        }
        return false;
    }

    public Boolean tryLock(String key, Long leastTime, TimeUnit timeUnit) {
        return redisCacheHelper.setIfAbsent(Constants.LOCK_PREFIX + key, key, leastTime, timeUnit);
    }

    public void unlock(String key) {
        redisCacheHelper.delete(Constants.LOCK_PREFIX + key);
    }
}
