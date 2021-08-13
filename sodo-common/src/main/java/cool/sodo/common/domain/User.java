package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "user")
public class User implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    private String nickname;

    private String description;

    private String avatarUrl;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String openId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String unionId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clientId;

    private String schoolId;

    private String phone;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String language;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date loginAt;

    public void init() {

        this.userId = null;
        this.avatarUrl = null;
        this.openId = null;
        this.unionId = null;
        this.schoolId = null;
        this.city = null;
        this.province = null;
    }
}
