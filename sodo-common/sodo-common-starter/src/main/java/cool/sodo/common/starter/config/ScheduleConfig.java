package cool.sodo.common.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author TimeChaser
 * @date 2021/10/12 14:57
 */
@Configuration(proxyBeanMethods = false)
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                r -> new Thread(r, "sodo-schedule")
        ));
    }
}
