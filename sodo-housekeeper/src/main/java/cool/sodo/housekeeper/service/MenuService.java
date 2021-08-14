package cool.sodo.housekeeper.service;

import cool.sodo.common.domain.Menu;
import cool.sodo.housekeeper.entity.MenuRequest;
import cool.sodo.housekeeper.entity.MenuVO;

import java.util.List;

public interface MenuService {

    void insertMenu(Menu menu, String userId, String clientId);

    void deleteMenu(String menuId, String userId);

    void updateMenu(Menu menu, String userId);

    void updateMenu(Menu menu);

    Menu getMenu(String menuId);

    Menu getMenuNullable(String menuId);

    List<MenuVO> tree(String clientId);

    List<MenuVO> listMenu(MenuRequest menuRequest);
}
