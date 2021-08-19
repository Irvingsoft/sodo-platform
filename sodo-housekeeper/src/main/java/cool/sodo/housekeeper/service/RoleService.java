package cool.sodo.housekeeper.service;

import cool.sodo.common.domain.Role;
import cool.sodo.housekeeper.entity.RoleRequest;
import cool.sodo.housekeeper.entity.RoleVO;

import java.util.List;

public interface RoleService {

    void insertRole(Role role, String userId);

    void deleteRole(String roleId, String userId);

    void deleteRole(List<String> roleIdList, String userId);

    void updateRole(Role role, String userId);

    void updateRole(Role role);

    Role getRole(String roleId);

    Role getRoleNullable(String roleId);

    List<RoleVO> tree(String clientId);

    List<RoleVO> listRole(RoleRequest roleRequest);
}
