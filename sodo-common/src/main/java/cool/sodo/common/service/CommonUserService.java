package cool.sodo.common.service;

import cool.sodo.common.domain.User;

public interface CommonUserService {

    User getUserBase(String userId);

    User getUserIdentityByIdentity(String identity);

    void checkUserUniqueness(User user);
}
