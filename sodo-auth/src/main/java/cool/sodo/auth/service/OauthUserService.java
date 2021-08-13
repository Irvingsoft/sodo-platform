package cool.sodo.auth.service;

import cool.sodo.common.domain.OauthUser;

public interface OauthUserService {

    Integer countOauthUserById(String openId);

    Integer saveOauthUser(OauthUser oauthUser);

    OauthUser getOauthUserIdentityByOpenId(String identity);
}
