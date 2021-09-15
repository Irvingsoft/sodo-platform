package cool.sodo.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author TimeChaser
 * @date 2021/9/15 20:52
 */
@Data
@TableName(value = "access_token")
public class AccessToken implements Serializable {

    @TableId
    private String token;

    private String identity;

    private String clientId;

    private Date expireAt;

    private Date updateAt;
}
