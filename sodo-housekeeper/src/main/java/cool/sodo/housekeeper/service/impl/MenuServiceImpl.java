package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.core.domain.Menu;
import cool.sodo.common.starter.entity.MenuVO;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.core.mapper.CommonMenuMapper;
import cool.sodo.common.base.util.BeanUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.node.ForestNodeMerger;
import cool.sodo.housekeeper.entity.MenuDTO;
import cool.sodo.housekeeper.service.MenuService;
import cool.sodo.housekeeper.service.RoleToMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TimeChaser
 * @date 2021/8/14 13:43
 */
@Service
public class MenuServiceImpl implements MenuService {

    public static final int SELECT_TREE = 0;
    public static final int SELECT_LIST = 1;
    public static final int SELECT_OTHER = 2;

    @Resource
    private CommonMenuMapper menuMapper;
    @Resource
    private RoleToMenuService roleToMenuService;

    private LambdaQueryWrapper<Menu> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_TREE:
                menuLambdaQueryWrapper.select(Menu::getMenuId, Menu::getParentId, Menu::getName, Menu::getSort);
                break;
            case SELECT_LIST:
                menuLambdaQueryWrapper.select(Menu::getMenuId, Menu::getParentId, Menu::getCode,
                        Menu::getName, Menu::getIcon, Menu::getPath, Menu::getDescription, Menu::getSort,
                        Menu::getMenuType, Menu::getButtonType, Menu::getNewWindow,
                        Menu::getCreateAt, Menu::getUpdateAt, Menu::getCreateBy, Menu::getUpdateBy);
                break;
            default:
                break;
        }
        menuLambdaQueryWrapper.orderByAsc(Menu::getSort);
        return menuLambdaQueryWrapper;
    }

    private List<MenuVO> toMenuVO(List<Menu> menuList) {
        return menuList.stream().map(x -> BeanUtil.copy(x, MenuVO.class)).collect(Collectors.toList());
    }

    @Override
    public void insertMenu(Menu menu, String userId) {

        if (StringUtil.isEmpty(menu.getClientId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "ClientId 不能为空！");
        }
        if (!StringUtil.isEmpty(menu.getMenuId()) &&
                !StringUtil.isEmpty(getMenuNullable(menu.getMenuId()))) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "已存在 MenuId 为 " + menu.getMenuId() + " 的记录！");
        }
        menu.init(userId);
        if (menuMapper.insert(menu) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "插入 Menu 记录失败！");
        }
    }

    @Override
    public void deleteMenu(String menuId, String userId) {

        Menu menu = getMenu(menuId);
        menu.delete(userId);
        updateMenu(menu);
        if (menuMapper.deleteById(menuId) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 Menu 记录失败！");
        }
    }

    @Override
    public void deleteMenu(List<String> menuIdList, String userId) {

        if (StringUtil.isEmpty(menuIdList)) {
            return;
        }
        for (String menuId : menuIdList) {
            Menu menu = getMenu(menuId);
            menu.delete(userId);
            updateMenu(menu);
        }
        if (menuMapper.deleteBatchIds(menuIdList) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 Menu 记录失败！");
        }
    }

    @Override
    public void updateMenu(Menu menu, String userId) {

        if (StringUtil.isEmpty(menu.getMenuId())) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "MenuId 不能为空！");
        }
        Menu menuOld = getMenu(menu.getMenuId());
        menuOld.update(menu, userId);
        if (menuMapper.updateById(menuOld) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "更新 Menu 记录失败！");
        }
    }

    @Override
    public void updateMenu(Menu menu) {
        if (menuMapper.updateById(menu) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "更新 Menu 记录失败！");
        }
    }

    @Override
    public Menu getMenu(String menuId) {

        Menu menu = menuMapper.selectById(menuId);
        if (StringUtil.isEmpty(menu)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "不存在 MenuId 为 " + menuId + " 的记录！");
        }
        return menu;
    }

    @Override
    public Menu getMenuNullable(String menuId) {
        return menuMapper.selectById(menuId);
    }

    @Override
    public List<MenuVO> treeMenu(String clientId) {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_TREE);
        menuLambdaQueryWrapper.eq(Menu::getClientId, clientId);
        List<Menu> menuList = menuMapper.selectList(menuLambdaQueryWrapper);
        return ForestNodeMerger.merge(toMenuVO(menuList));
    }

    @Override
    public List<MenuVO> treeMenu(List<String> roleIdList) {

        if (StringUtil.isEmpty(roleIdList)) {
            return null;
        }
        List<String> menuIdList = roleToMenuService.listMenuIdByRole(roleIdList);
        if (StringUtil.isEmpty(menuIdList)) {
            return null;
        }
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_TREE);
        menuLambdaQueryWrapper.in(Menu::getMenuId, menuIdList);
        List<Menu> menuList = menuMapper.selectList(menuLambdaQueryWrapper);
        return ForestNodeMerger.merge(toMenuVO(menuList));
    }

    @Override
    public List<String> listMenu(String roleId) {

        List<String> menuIdList = roleToMenuService.listMenuIdByRole(roleId);
        if (StringUtil.isEmpty(menuIdList)) {
            return null;
        }
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_OTHER);
        menuLambdaQueryWrapper.in(Menu::getMenuId, menuIdList)
                .select(Menu::getMenuId);
        return menuMapper.selectList(menuLambdaQueryWrapper)
                .stream()
                .map(Menu::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuVO> listMenu(MenuDTO menuDTO) {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_LIST);
        menuLambdaQueryWrapper.eq(Menu::getClientId, menuDTO.getClientId());
        if (!StringUtil.isEmpty(menuDTO.getContent())) {
            menuLambdaQueryWrapper.and(wrapper -> wrapper.like(Menu::getName, menuDTO.getContent())
                    .or()
                    .like(Menu::getPath, menuDTO.getContent())
                    .or()
                    .like(Menu::getIcon, menuDTO.getContent())
                    .or()
                    .like(Menu::getDescription, menuDTO.getContent()));
        }
        List<Menu> menuList = menuMapper.selectList(menuLambdaQueryWrapper);
        return ForestNodeMerger.merge(toMenuVO(menuList));
    }
}
