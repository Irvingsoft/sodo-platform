package cool.sodo.common.service;

import cool.sodo.common.domain.AccessToken;

public interface CommonAccessTokenService {

    AccessToken getAccessTokenNullableByIdentity(String identity);

    AccessToken getAccessToken(String token);

    AccessToken getAccessTokenCache(String token);

    void updateAccessToken(AccessToken accessToken);

    void removeAccessTokenByToken(String token);

    void insertAccessToken(AccessToken accessToken);

    void checkAccessToken(AccessToken accessToken, String clientId);

    boolean validateAccessToken(String token);
}
