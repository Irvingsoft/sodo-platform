package cool.sodo.housekeeper.service;

import cool.sodo.common.domain.RoleToMenu;

import java.util.List;

public interface RoleToMenuService {

    void insert(List<RoleToMenu> roleToMenuList);

    void deleteByRole(String roleId);

    void deleteByRole(List<String> roleIdList);

    void deleteByMenu(String menuId);

    void deleteByMenu(List<String> menuIdList);

    List<String> listRoleToMenuMenuIdByRole(String roleId);

    List<String> listRoleToMenuMenuIdByRole(List<String> roleIdList);
}
