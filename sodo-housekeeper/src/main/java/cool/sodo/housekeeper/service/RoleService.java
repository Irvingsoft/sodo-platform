package cool.sodo.housekeeper.service;

import cool.sodo.common.core.domain.Role;
import cool.sodo.housekeeper.entity.RoleDTO;
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

    List<RoleVO> treeRoleByClient(String clientId);

    List<RoleVO> treeRoleByUser(String userId);

    List<String> listRole(String userId);

    List<RoleVO> listRole(RoleDTO roleDTO);

    void grant(List<String> roleIdList, List<String> menuIdList);
}
