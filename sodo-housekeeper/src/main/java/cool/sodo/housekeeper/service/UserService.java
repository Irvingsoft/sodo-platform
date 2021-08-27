package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.User;
import cool.sodo.housekeeper.entity.UserDTO;

import java.util.List;

public interface UserService {

    void insert(User user, String userId);

    void delete(String userId, String deleteBy);

    void delete(List<String> userIdList, String deleteBy);

    void update(User user);

    void update(User user, String updateBy);

    void grant(String userId, List<String> roleIdList);

    void grant(List<String> userIdList, List<String> roleIdList);

    User get(String userId);

    User getInfoDetail(String userId);

    IPage<User> pageBaseDetail(UserDTO userDTO);
}
