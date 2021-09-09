package cool.sodo.common.base.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "oauth_user")
public class OauthUser implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String openId;

    private String unionId;

    private String clientId;

    private String nickName;

    private String avatarUrl;

    private String phone;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String language;

    private Date createAt;

    private Date updateAt;
}
