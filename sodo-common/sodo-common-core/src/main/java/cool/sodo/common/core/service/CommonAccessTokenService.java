package cool.sodo.common.core.service;

import cool.sodo.common.core.domain.AccessToken;

public interface CommonAccessTokenService {

    AccessToken getByIdentity(String identity);

    AccessToken get(String token);

    AccessToken getFromCache(String token);
}
