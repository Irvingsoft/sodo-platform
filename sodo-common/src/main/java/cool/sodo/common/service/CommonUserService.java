package cool.sodo.common.service;

import cool.sodo.common.domain.User;

public interface CommonUserService {

    User getUserBase(String userId);

    User getUserIdentityByIdentity(String identity);

    User getUserByIdentity(String identity);

    boolean validateUser(String identity);

    void checkUserStatus(String identity);
}
