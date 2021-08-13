package cool.sodo.auth.service;

import cool.sodo.auth.entity.WechatToken;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.OauthUser;

public interface WechatAuthService {

    WechatToken getAccessToken(String code, OauthClient oauthClient);

    OauthUser getUserInfo(String accessToken, String openId);
}
