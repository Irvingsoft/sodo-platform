package cool.sodo.zuul.service;

import cool.sodo.common.domain.OauthIp;

import java.util.List;

public interface OauthIpService {

    boolean validateOauthIp(String clientId, String ip);

    List<OauthIp> listOauthIpValidIdentityByClient(String clientId);

    List<OauthIp> listOauthIpNotValidIdentityByClient(String clientId);
}
