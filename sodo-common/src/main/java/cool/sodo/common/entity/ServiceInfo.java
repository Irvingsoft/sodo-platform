package cool.sodo.common.entity;

import cool.sodo.common.util.INetUtil;
import lombok.Data;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @description:
 * @date 2021/6/16 16:31
 */
@Component
@ConfigurationProperties(
        prefix = "spring.application",
        ignoreUnknownFields = false
)
@Data
public class ServiceInfo implements SmartInitializingSingleton {

    @Resource
    private final ServerProperties serverProperties;
    private String name;
    private String path;
    private String env;
    private String hostName;
    private String ip;
    private Integer port;
    private String ipColonPort;

    @Override
    public void afterSingletonsInstantiated() {

        this.hostName = INetUtil.getHostName();
        this.ip = INetUtil.getHostIp();
        this.port = serverProperties.getPort();
        this.ipColonPort = String.format("%s:%d", this.ip, this.port);
    }
}
