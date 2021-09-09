package cool.sodo.common.base.service;

import cool.sodo.common.base.domain.AccessToken;

public interface CommonAccessTokenService {

    AccessToken getByIdentity(String identity);

    AccessToken get(String token);

    AccessToken getFromCache(String token);
}
