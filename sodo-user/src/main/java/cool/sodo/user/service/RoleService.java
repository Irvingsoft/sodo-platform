package cool.sodo.user.service;

import cool.sodo.common.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> listRole(String userId, String clientId);

    List<String> listRoleRoleId(String userId, String clientId);
}
