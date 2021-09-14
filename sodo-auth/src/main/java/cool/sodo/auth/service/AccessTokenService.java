package cool.sodo.auth.service;

import cool.sodo.common.core.domain.AccessToken;

import java.util.List;

public interface AccessTokenService {

    void insert(AccessToken accessToken);

    void deleteCache(String token);

    void deleteCacheByIdentity(String identity);

    void update(AccessToken accessToken);

    AccessToken getByIdentity(String identity);

    List<String> listToken(String identity);
}
