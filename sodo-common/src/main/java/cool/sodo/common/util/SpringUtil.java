package cool.sodo.common.util;

import io.micrometer.core.lang.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类，注册成组件后自动装配 ApplicationContext
 *
 * @author TimeChaser
 * @date 2021/6/12 18:36
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static void publishEvent(ApplicationEvent applicationEvent) {

        if (StringUtil.isEmpty(applicationContext)) {
            return;
        }
        applicationContext.publishEvent(applicationEvent);
    }
}
