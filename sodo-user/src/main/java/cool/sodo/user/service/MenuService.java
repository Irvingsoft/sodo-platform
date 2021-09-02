package cool.sodo.user.service;

import cool.sodo.common.domain.Menu;
import cool.sodo.user.entity.MenuVO;

import java.util.List;

public interface MenuService {

    List<MenuVO> route(List<String> roleIdList);

    List<MenuVO> button(List<String> roleIdList);

    List<Menu> listMenu(List<String> roleIdList, Integer menuType);
}
