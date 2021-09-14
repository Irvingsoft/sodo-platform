package cool.sodo.common.core.service;

import cool.sodo.common.core.domain.OauthClient;

public interface CommonOauthClientService {

    /**
     * 获取客户端身份信息
     *
     * @param clientId
     * @return cool.sodo.common.starter.domain.OauthClient
     */
    OauthClient getOauthClientIdentity(String clientId);
}
