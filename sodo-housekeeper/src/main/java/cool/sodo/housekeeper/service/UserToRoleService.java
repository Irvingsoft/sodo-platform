package cool.sodo.housekeeper.service;

import java.util.List;

public interface UserToRoleService {

    void deleteByRole(String roleId);

    void deleteByRole(List<String> roleIdList);

    List<String> listUserToRoleRoleId(String userId);
}
