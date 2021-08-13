package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口与客户端对应关系
 *
 * @author TimeChaser
 * @date 2021/5/30 12:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "client_to_api")
public class ClientApi implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String clientId;

    private String apiId;

    private Date createAt;

    private String createBy;

    public ClientApi(String clientId, String apiId, String createBy) {
        this.clientId = clientId;
        this.apiId = apiId;
        this.createBy = createBy;
        this.createAt = new Date();
    }
}
