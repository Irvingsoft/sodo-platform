package cool.sodo.common.service;

import cool.sodo.common.domain.AccessToken;

public interface CommonAccessTokenService {

    AccessToken getByIdentity(String identity);

    AccessToken get(String token);

    AccessToken getFromCache(String token);
}
