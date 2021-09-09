package cool.sodo.common.base.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
