package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.common.entity.LogAbstract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 接口日志实体类
 *
 * @author TimeChaser
 * @date 2021/6/16 12:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "log_api")
public class LogApi extends LogAbstract implements Serializable {

    private String apiId;

    private Integer time;

    private String requestBody;

    private Integer responseStatus;

    private String responseBody;

    @TableField(exist = false)
    private String apiName;
    @TableField(exist = false)
    private String apiPath;

    @Override
    public String toString() {
        return "LogApi{" +
                "apiId='" + apiId + '\'' +
                ", time=" + time +
                ", responseStatus='" + responseStatus + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", apiName='" + apiName + '\'' +
                ", apiPath='" + apiPath + '\'' +
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
                ", requestBody='" + requestBody + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
