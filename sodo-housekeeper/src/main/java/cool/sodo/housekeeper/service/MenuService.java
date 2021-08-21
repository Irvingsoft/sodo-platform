package cool.sodo.housekeeper.service;

import cool.sodo.common.domain.Menu;
import cool.sodo.housekeeper.entity.MenuDTO;
import cool.sodo.housekeeper.entity.MenuVO;

import java.util.List;

public interface MenuService {

    void insertMenu(Menu menu, String userIdId);

    void deleteMenu(String menuId, String userId);

    void deleteMenu(List<String> menuIdList, String userId);

    void updateMenu(Menu menu, String userId);

    void updateMenu(Menu menu);

    Menu getMenu(String menuId);

    Menu getMenuNullable(String menuId);

    List<MenuVO> treeMenu(String clientId);

    List<MenuVO> treeMenu(List<String> roleIdList);

    List<String> listMenu(String roleId);

    List<MenuVO> listMenu(MenuDTO menuDTO);
}
