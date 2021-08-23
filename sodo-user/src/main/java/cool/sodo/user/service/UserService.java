package cool.sodo.user.service;

import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.User;
import cool.sodo.user.entity.PasswordDTO;
import cool.sodo.user.entity.UserInsertRequest;
import cool.sodo.user.entity.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    void insertUser(User user, OauthClient client);

    void insertUserMq(User user);

    void updateUser(UserUpdateRequest userUpdateRequest, User user);

    void updatePassword(PasswordDTO passwordDTO, User user);

    void updateUserLogin(String identity, String loginIp);

    User getUserBase(String id);

    User getUserGeneral(String id);

    boolean validateUsername(String username);

    boolean validatePhone(String phone);

    boolean validatePassword(String password);

    User initUser(UserInsertRequest userInsertRequest, HttpServletRequest request);

    void decryptRsaPassword(User user);
}
