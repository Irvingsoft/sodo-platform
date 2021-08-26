package cool.sodo.common.service;

import cool.sodo.common.domain.OauthClient;

public interface CommonOauthClientService {

    /**
     * 获取客户端身份信息
     *
     * @param clientId
     * @return cool.sodo.common.domain.OauthClient
     */
    OauthClient getOauthClientIdentity(String clientId);
}
