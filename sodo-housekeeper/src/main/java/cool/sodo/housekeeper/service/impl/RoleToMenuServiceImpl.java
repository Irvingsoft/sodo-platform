package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.RoleToMenu;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonRoleToMenuMapper;
import cool.sodo.common.service.impl.CommonRoleToMenuServiceImpl;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.service.RoleToMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleToMenuServiceImpl extends CommonRoleToMenuServiceImpl implements RoleToMenuService {

    @Resource
    private CommonRoleToMenuMapper roleToMenuMapper;

    @Override
    public void insert(List<RoleToMenu> roleToMenuList) {

        for (RoleToMenu roleToMenu : roleToMenuList) {
            if (roleToMenuMapper.insert(roleToMenu) <= 0) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "新增 RoleToMenu 失败！");
            }
        }
    }

    @Override
    public void deleteByRole(String roleId) {

        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.eq(RoleToMenu::getRoleId, roleId);
        if (roleToMenuMapper.delete(roleToMenuLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 RoleToMenu 失败！");
        }
    }

    @Override
    public void deleteByRole(List<String> roleIdList) {

        if (StringUtil.isEmpty(roleIdList)) {
            return;
        }
        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.in(RoleToMenu::getRoleId, roleIdList);
        if (roleToMenuMapper.delete(roleToMenuLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 RoleToMenu 失败！");
        }
    }

    @Override
    public void deleteByMenu(String menuId) {

        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.eq(RoleToMenu::getMenuId, menuId);
        if (roleToMenuMapper.delete(roleToMenuLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 RoleToMenu 失败！");
        }
    }

    @Override
    public void deleteByMenu(List<String> menuIdList) {

        if (StringUtil.isEmpty(menuIdList)) {
            return;
        }
        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.in(RoleToMenu::getMenuId, menuIdList);
        if (roleToMenuMapper.delete(roleToMenuLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 RoleToMenu 失败！");
        }
    }

    @Override
    public List<String> listRoleToMenuMenuIdByRole(List<String> roleIdList) {
        return super.listRoleToMenuMenuIdByRole(roleIdList);
    }

    @Override
    public List<String> listRoleToMenuMenuIdByRole(String roleId) {
        return super.listRoleToMenuMenuIdByRole(roleId);
    }
}
