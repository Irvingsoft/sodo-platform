package cool.sodo.housekeeper.service;

import cool.sodo.common.domain.RoleToMenu;

import java.util.List;

public interface RoleToMenuService {

    void insert(List<RoleToMenu> roleToMenuList);

    void deleteByRole(List<String> roleIdList);
}
