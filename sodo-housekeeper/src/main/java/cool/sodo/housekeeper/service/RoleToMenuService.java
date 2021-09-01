package cool.sodo.housekeeper.service;

import java.util.List;

public interface RoleToMenuService {

    void insertByRole(String roleId, List<String> menuIdList);

    void deleteByRole(String roleId);

    void deleteByRole(List<String> roleIdList);

    void deleteByMenu(String menuId);

    void deleteByMenu(List<String> menuIdList);

    List<String> listMenuIdByRole(String roleId);

    List<String> listMenuIdByRole(List<String> roleIdList);
}
