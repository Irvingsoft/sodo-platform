package cool.sodo.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.User;
import cool.sodo.user.entity.PasswordUpdateRequest;
import cool.sodo.user.entity.UserInsertRequest;
import cool.sodo.user.entity.UserRequest;
import cool.sodo.user.entity.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    void insertUser(User user);

    void insertUserMq(User user);

    void updateUser(UserUpdateRequest userUpdateRequest, User user);

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest, User user);

    void updateUserLogin(String identity);

    User getUserBase(String id);

    User getUserGeneral(String id);

    User getUserInfo(String id);

    IPage<User> pageUserBase(UserRequest userRequest);

    boolean validateUsername(String username);

    boolean validatePhone(String phone);

    boolean validatePassword(String password);

    User initUser(UserInsertRequest userInsertRequest, HttpServletRequest request);

    void decryptRsaPassword(User user, HttpServletRequest request);
}
