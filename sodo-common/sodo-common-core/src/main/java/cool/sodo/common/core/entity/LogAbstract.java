package cool.sodo.common.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志实体基类
 *
 * @author TimeChaser
 * @date 2021/6/21 18:53
 */
@Data
public class LogAbstract implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    protected String id;

    protected String serviceId;

    protected String serviceIp;

    protected String serviceHost;

    protected String env;

    protected String clientId;

    protected String userId;

    protected String requestId;

    protected String userIp;

    protected String systemName;

    protected String browserName;

    protected String requestUrl;

    protected String requestMethod;

    protected String className;

    protected String methodName;

    protected Date createAt;

    @Override
    public String toString() {
        return "LogAbstract{" +
                "id='" + id + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", serviceIp='" + serviceIp + '\'' +
                ", serviceHost='" + serviceHost + '\'' +
                ", env='" + env + '\'' +
                ", clientId='" + clientId + '\'' +
                ", userId='" + userId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", userIp='" + userIp + '\'' +
                ", systemName='" + systemName + '\'' +
                ", browserName='" + browserName + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
