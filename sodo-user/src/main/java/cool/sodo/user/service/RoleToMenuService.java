package cool.sodo.user.service;

import cool.sodo.common.domain.RoleToMenu;

import java.util.List;

public interface RoleToMenuService {

    List<RoleToMenu> listRoleToMenuByRole(List<String> roleIdList);

    List<String> listRoleToMenuMenuIdByRole(List<String> roleIdList);
}
