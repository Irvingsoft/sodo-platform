package cool.sodo.common.base.service;

import cool.sodo.common.base.domain.OauthClient;

public interface CommonOauthClientService {

    /**
     * 获取客户端身份信息
     *
     * @param clientId
     * @return cool.sodo.common.base.domain.OauthClient
     */
    OauthClient getOauthClientIdentity(String clientId);
}
