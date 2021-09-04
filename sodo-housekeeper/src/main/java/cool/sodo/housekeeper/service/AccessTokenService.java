package cool.sodo.housekeeper.service;

import cool.sodo.common.domain.User;

import java.util.List;

public interface AccessTokenService {

    void deleteCacheByClient(String clientId);

    void deleteCacheByIdentity(String identity);

    List<String> listTokenByIdentity(String identity);

    List<String> listTokenByClient(String clientId);

    Boolean online(User user);
}
