package cool.sodo.user.service;

import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.User;
import cool.sodo.user.entity.PasswordDTO;
import cool.sodo.user.entity.UserUpdateRequest;

public interface UserService {

    void insert(User user, OauthClient client);

    void insertFromMq(User user);

    void update(UserUpdateRequest userUpdateRequest, User user);

    void updatePassword(PasswordDTO passwordDTO, User user);

    void updateUserLogin(String identity, String loginIp);

    User getBase(String id);

    User getGeneral(String id);

    boolean validatePassword(String password);

    void decryptRsaPassword(User user);
}
