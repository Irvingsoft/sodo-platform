package cool.sodo.auth.service;

import cool.sodo.common.domain.OauthUser;

public interface OauthUserService {

    Integer countByOpenId(String openId);

    Integer insert(OauthUser oauthUser);
}
