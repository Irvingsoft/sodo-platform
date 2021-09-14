package cool.sodo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.domain.Menu;
import cool.sodo.common.base.entity.MenuVO;
import cool.sodo.common.base.mapper.CommonMenuMapper;
import cool.sodo.common.base.service.CommonRoleToMenuService;
import cool.sodo.common.base.util.BeanUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.node.ForestNodeMerger;
import cool.sodo.user.common.Constants;
import cool.sodo.user.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author TimeChaser
 * @date 2021/9/2 18:58
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private CommonMenuMapper menuMapper;
    @Resource
    private CommonRoleToMenuService roleToMenuService;

    private LambdaQueryWrapper<Menu> generateQueryWrapperInUse() {
        return Wrappers.lambdaQuery();
    }

    /**
     * 查询路由列表
     *
     * @param roleIdList 角色 ID 列表
     * @return java.util.List<cool.sodo.user.entity.MenuVO>
     */
    @Override
    public List<MenuVO> route(List<String> roleIdList) {

        if (roleIdList.isEmpty()) {
            return null;
        }
        List<Menu> allMenuList = listMenu(null, Constants.MENU_TYPE_MENU);
        List<Menu> roleMenuList = listMenu(roleIdList, Constants.MENU_TYPE_MENU);
        LinkedList<Menu> routeMenuList = new LinkedList<>(roleMenuList);
        roleMenuList.forEach(roleMenu -> recursionMenuParent(allMenuList, routeMenuList, roleMenu));
        routeMenuList.sort(Comparator.comparing(Menu::getSort));

        return ForestNodeMerger.merge(toMenuVO(routeMenuList));
    }

    /**
     * 递归查询父菜单并添加进路由列表
     *
     * @param allMenuList   所有菜单列表
     * @param routeMenuList 路由菜单列表
     * @param menu          目标菜单
     */
    private void recursionMenuParent(List<Menu> allMenuList, List<Menu> routeMenuList, Menu menu) {

        Optional<Menu> parent = allMenuList.stream().filter(x -> x.getMenuId().equals(menu.getParentId())).findFirst();
        if (parent.isPresent() && !routeMenuList.contains(parent.get())) {
            routeMenuList.add(parent.get());
            recursionMenuParent(allMenuList, routeMenuList, parent.get());
        }
    }

    private List<MenuVO> toMenuVO(List<Menu> menuList) {
        return menuList.stream().map(x -> BeanUtil.copy(x, MenuVO.class)).collect(Collectors.toList());
    }

    /**
     * 查询按钮及其所属的页面
     *
     * @param roleIdList 角色 ID 列表
     * @return java.util.List<cool.sodo.user.entity.MenuVO>
     */
    @Override
    public List<MenuVO> button(List<String> roleIdList) {

        List<Menu> roleMenuList = listMenu(roleIdList, Constants.MENU_TYPE_BUTTON);
        LinkedList<Menu> buttonMenuList = new LinkedList<>(roleMenuList);
        buttonMenuList.sort(Comparator.comparing(Menu::getSort));

        return ForestNodeMerger.merge(toMenuVO(buttonMenuList));
    }

    @Override
    public List<Menu> listMenu(List<String> roleIdList, Integer menuType) {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = generateQueryWrapperInUse();
        if (!StringUtil.isEmpty(roleIdList)) {
            menuLambdaQueryWrapper.in(Menu::getMenuId, roleToMenuService.listMenuIdByRole(roleIdList));
        }
        if (!StringUtil.isEmpty(menuType)) {
            menuLambdaQueryWrapper.eq(Menu::getMenuType, menuType);
        }
        return menuMapper.selectList(menuLambdaQueryWrapper);
    }
}
