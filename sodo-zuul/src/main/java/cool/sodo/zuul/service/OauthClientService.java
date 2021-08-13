package cool.sodo.zuul.service;

import cool.sodo.common.domain.OauthClient;

public interface OauthClientService {

    boolean validateClient(String cliendId);

    OauthClient getOauthClientIdentity(String clientId);

}
