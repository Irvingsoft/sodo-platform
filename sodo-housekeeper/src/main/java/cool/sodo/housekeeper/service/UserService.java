package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.User;
import cool.sodo.housekeeper.entity.UserDTO;

import java.util.List;

public interface UserService {

    void insertUser(User user, String userId);

    void deleteUser(String userId, String deleteBy);

    void updateUser(User user);

    void updateUser(User user, String updateBy);

    void grant(List<String> userIdList, List<String> roleIdList);

    User getUser(String userId);

    User getUserInfoDetail(String userId);

    IPage<User> pageUserBaseDetail(UserDTO userDTO);
}
