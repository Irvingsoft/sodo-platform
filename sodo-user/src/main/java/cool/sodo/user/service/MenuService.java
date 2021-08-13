package cool.sodo.user.service;

import cool.sodo.common.domain.Menu;
import cool.sodo.user.entity.MenuVO;

import java.util.List;

public interface MenuService {

    List<MenuVO> route(List<String> roleIdList, String clientId);

    List<MenuVO> button(List<String> roleIdList, String clientId);

    List<Menu> listMenuInUse(List<String> menuIdList);

    List<Menu> listMenuInUse(List<String> roleIdList, String clientId, Integer menuType);
}
