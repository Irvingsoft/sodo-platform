package cool.sodo.eureka.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Eureka 状态监听器
 *
 * @author TimeChaser
 * @date 2021/8/19 14:37
 */
@Component
@Slf4j
public class EurekaStatusListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        log.info(event.getServerId() + "\t" + event.getAppName() + " 服务下线");
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        log.info(event.getInstanceInfo().getAppName() + " 进行注册");
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        log.info(event.getServerId() + "\t" + event.getAppName() + " 服务进行续约");
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        log.info("注册中心 启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        log.info("Eureka Server 启动");
    }
}
