package cool.sodo.zuul.service;

import cool.sodo.common.domain.OauthClient;

public interface OauthClientService {

    boolean isInUse(String clientId);

    OauthClient getOauthClientIdentity(String clientId);

    boolean isSignature(String clientId);
}
