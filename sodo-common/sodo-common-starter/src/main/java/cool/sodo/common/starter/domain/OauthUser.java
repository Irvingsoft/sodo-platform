package cool.sodo.common.starter.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "oauth_user")
public class OauthUser implements Serializable {

    @TableId
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
