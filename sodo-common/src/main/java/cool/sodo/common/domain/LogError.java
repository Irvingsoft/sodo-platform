package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.common.entity.LogAbstract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 错误日志实体类
 *
 * @author TimeChaser
 * @date 2021/6/16 12:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "log_error")
public class LogError extends LogAbstract implements Serializable {

    private String stackTrace;

    private String exceptionName;

    private String message;

    private String fileName;

    private Integer lineNum;

    private String params;

    @Override
    public String toString() {
        return "LogError{" +
                "stackTrace='" + stackTrace + '\'' +
                ", exceptionName='" + exceptionName + '\'' +
                ", message='" + message + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lineNum=" + lineNum +
                ", params='" + params + '\'' +
                ", id='" + id + '\'' +
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
