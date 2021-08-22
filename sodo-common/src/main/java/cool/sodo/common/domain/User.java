package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.util.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "user")
public class User implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    private String nickname;

    private String description;

    private String avatarUrl;

    private String username;

    private String password;

    private String salt;

    private String openId;

    private String unionId;

    private String clientId;

    private String schoolId;

    private String phone;

    private String email;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String language;

    private Integer status;

    private Date loginAt;

    private String loginIp;

    private Date createAt;

    private Date updateAt;

    private String createBy;

    private String updateBy;

    @TableLogic
    private Boolean deleted;

    @TableField(exist = false)
    private List<String> codeList;
    @TableField(exist = false)
    private List<String> roleIdList;

    public void init(OauthClient oauthClient) {

        this.avatarUrl = null;
        this.openId = null;
        this.unionId = null;
        this.schoolId = null;
        this.city = null;
        this.province = null;
        this.clientId = oauthClient.getClientId();
        this.status = oauthClient.getUserStatus();
    }

    public void init(String userId) {

        if (StringUtil.isEmpty(this.username)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "字段 Username 不能为空！");
        }
        if (StringUtil.isEmpty(this.password)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "字段 Password 不能为空！");
        }
        if (StringUtil.isEmpty(this.clientId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "字段 ClientId 不能为空！");
        }
        if (StringUtil.isEmpty(this.status)) {
            this.status = Constants.USER_STATUS_FREEZE;
        }
        this.userId = null;
        this.openId = null;
        this.unionId = null;
        this.createBy = userId;
    }

    public void updatePassword(String password) {

        this.password = password;
        this.updateAt = new Date();
    }

    public void delete(String deleteBy) {

        this.updateBy = deleteBy;
        this.updateAt = new Date();
    }

    public void update(User user, String updateBy) {

        if (!StringUtil.isEmpty(user.getNickname())) {
            this.nickname = user.getNickname();
        }
        if (!StringUtil.isEmpty(user.getAvatarUrl())) {
            this.avatarUrl = user.getAvatarUrl();
        }
        this.description = user.getDescription();

        if (!StringUtil.isEmpty(user.getPassword())) {
            this.password = user.getPassword();
        }
        if (!StringUtil.isEmpty(user.getSchoolId())) {
            this.schoolId = user.getSchoolId();
        }
        if (!StringUtil.isEmpty(user.getGender())) {
            this.gender = user.getGender();
        }
        if (!StringUtil.isEmpty(user.getCountry())) {
            this.country = user.getCountry();
        }
        if (!StringUtil.isEmpty(user.getProvince())) {
            this.province = user.getProvince();
        }
        if (!StringUtil.isEmpty(user.getCity())) {
            this.city = user.getCity();
        }
        if (!StringUtil.isEmpty(user.getStatus())) {
            this.status = user.getStatus();
        }
        this.updateBy = updateBy;
        this.updateAt = new Date();
    }
}
