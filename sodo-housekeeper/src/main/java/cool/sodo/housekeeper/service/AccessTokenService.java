package cool.sodo.housekeeper.service;

import java.util.List;

public interface AccessTokenService {

    void deleteCacheByClient(String clientId);

    List<String> listTokenByClient(String clientId);
}
