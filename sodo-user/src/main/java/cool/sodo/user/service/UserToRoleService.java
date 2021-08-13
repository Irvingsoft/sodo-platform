package cool.sodo.user.service;

import cool.sodo.common.domain.UserToRole;

import java.util.List;

public interface UserToRoleService {

    List<UserToRole> listUserToRole(String userId);

    List<String> listUserToRoleRoleId(String userId);
}
