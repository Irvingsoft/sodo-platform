package cool.sodo.housekeeper.service;

import java.util.List;

public interface UserToRoleService {

    void insertByUser(String userId, List<String> roleIdList);

    void deleteByUser(String userId);

    void deleteByUser(List<String> userIdList);

    void deleteByRole(String roleId);

    void deleteByRole(List<String> roleIdList);

    List<String> listUserToRoleRoleId(String userId);
}
