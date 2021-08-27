package cool.sodo.user.service;

import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.User;
import cool.sodo.user.entity.PasswordDTO;
import cool.sodo.user.entity.UserUpdateDTO;

public interface UserService {

    void insert(User user, OauthClient client);

    void insertFromMq(User user);

    void update(UserUpdateDTO userUpdateDTO, User user);

    void updatePassword(PasswordDTO passwordDTO, User user);

    void updateUserLogin(String identity, String loginIp);

    User getBase(String id);

    User getGeneral(String id);

    boolean validatePassword(String password);

    void decryptRsaPassword(User user);

    void checkUsername(String username, String clientId);

    void checkPhone(String phone, String clientId);
}
