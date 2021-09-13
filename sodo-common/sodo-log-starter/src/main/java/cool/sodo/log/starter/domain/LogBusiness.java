package cool.sodo.log.starter.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.log.starter.entity.LogAbstract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 业务日志实体类
 *
 * @author TimeChaser
 * @date 2021/6/16 12:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "log_business")
public class LogBusiness extends LogAbstract implements Serializable {

    /**
     * INSERT/DELETE/UPDATE
     *
     * @author TimeChaser
     * @date 2021/6/16 19:32
     */
    private String businessType;

    /**
     * PathVariable
     *
     * @author TimeChaser
     * @date 2021/7/17 10:01
     */
    private String businessId;

    /**
     * RequestBody
     *
     * @author TimeChaser
     * @date 2021/7/17 10:01
     */
    private String businessData;

    private String message;

    @Override
    public String toString() {
        return "LogBusiness{" +
                ", businessType='" + businessType + '\'' +
                ", businessId='" + businessId + '\'' +
                ", businessData='" + businessData + '\'' +
                ", message='" + message + '\'' +
                ", " + super.toString() + '\'' +
                '}';
    }
}
