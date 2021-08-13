package cool.sodo.auth.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class WechatToken implements Serializable {

    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private String openId;

    /**
     * 微信小程序解密用户信息
     */
    private String sessionKey;
    private String scope;
    private String unionId;
}