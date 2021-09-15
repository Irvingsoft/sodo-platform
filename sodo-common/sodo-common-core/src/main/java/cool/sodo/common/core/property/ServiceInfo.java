package cool.sodo.common.core.property;

import cool.sodo.common.base.util.INetUtil;
import lombok.Data;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/15 12:03
 */
@ConfigurationProperties(prefix = "spring.application", ignoreUnknownFields = false)
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
