package cool.sodo.common.core.domain;

import com.baomidou.mybatisplus.annotation.*;
import cool.sodo.common.base.util.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户端信息
 *
 * @author TimeChaser
 * @date 2021/6/6 21:00
 */
@Data
@TableName(value = "oauth_client")
public class OauthClient implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String clientId;

    private String name;

    private String clientSecret;

    private String description;

    private Boolean inUse;

    private Boolean register;

    private Boolean captcha;

    private Boolean signature;

    private Boolean concurrentLogin;

    private Boolean shareToken;

    private Integer userStatus;

    private Integer tokenExpire;

    private String redirectUri;

    private Date createAt;

    private Date updateAt;

    private String createBy;

    private String updateBy;

    @TableLogic
    private Boolean deleted;

    @TableField(exist = false)
    private User creator;
    @TableField(exist = false)
    private User updater;
    @TableField(exist = false)
    private List<String> apiIdList;

    public void init(String userId) {

        if (!StringUtil.isEmpty(this.tokenExpire) && this.tokenExpire <= 0) {
            this.tokenExpire = null;
        }
        this.createBy = userId;
    }

    public void update(OauthClient oauthClient, String userId) {

        if (!StringUtil.isEmpty(oauthClient.getName())) {
            this.name = oauthClient.getName();
        }
        if (!StringUtil.isEmpty(oauthClient.getClientSecret())) {
            this.clientSecret = oauthClient.getClientSecret();
        }
        if (!StringUtil.isEmpty(oauthClient.getDescription())) {
            this.description = oauthClient.getDescription();
        }
        if (!StringUtil.isEmpty(oauthClient.getInUse())) {
            this.inUse = oauthClient.getInUse();
        }
        if (!StringUtil.isEmpty(oauthClient.getRegister())) {
            this.register = oauthClient.getRegister();
        }
        if (!StringUtil.isEmpty(oauthClient.getCaptcha())) {
            this.captcha = oauthClient.getCaptcha();
        }
        if (!StringUtil.isEmpty(oauthClient.getSignature())) {
            this.signature = oauthClient.getSignature();
        }
        if (!StringUtil.isEmpty(oauthClient.getConcurrentLogin())) {
            this.concurrentLogin = oauthClient.getConcurrentLogin();
        }
        if (!StringUtil.isEmpty(oauthClient.getShareToken())) {
            this.shareToken = oauthClient.getShareToken();
        }
        if (!StringUtil.isEmpty(oauthClient.getUserStatus())) {
            this.userStatus = oauthClient.getUserStatus();
        }
        if (!StringUtil.isEmpty(oauthClient.getTokenExpire())) {
            this.tokenExpire = oauthClient.getTokenExpire();
        }
        if (!StringUtil.isEmpty(oauthClient.getRedirectUri())) {
            this.redirectUri = oauthClient.getRedirectUri();
        }
        this.updateBy = userId;
        this.updateAt = new Date();

        if (!StringUtil.isEmpty(this.tokenExpire) && this.tokenExpire <= 0) {
            this.tokenExpire = null;
        }
    }

    public void delete(String userId) {
        this.updateBy = userId;
        this.updateAt = new Date();
    }
}
