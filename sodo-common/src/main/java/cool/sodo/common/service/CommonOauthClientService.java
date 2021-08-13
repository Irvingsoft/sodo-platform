package cool.sodo.common.service;

import cool.sodo.common.domain.OauthClient;

import javax.servlet.http.HttpServletRequest;

public interface CommonOauthClientService {

    OauthClient getOauthClientIdentity(String clientId);

    boolean validateOauthClientRegister(String clientId);

    void checkOauthClientRegister(HttpServletRequest request);
}
