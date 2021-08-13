package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "client_menu")
@Deprecated
public class ClientMenu implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String clientId;

    private String path;

    private Date createAt;

    private Date updateAt;

    private String createBy;

    private String updateBy;
}
