package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.core.domain.RoleToMenu;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.core.mapper.CommonRoleToMenuMapper;
import cool.sodo.common.core.service.impl.CommonRoleToMenuServiceImpl;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.housekeeper.service.RoleToMenuService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Primary
public class RoleToMenuServiceImpl extends CommonRoleToMenuServiceImpl implements RoleToMenuService {

    @Resource
    private CommonRoleToMenuMapper roleToMenuMapper;

    @Override
    public void insertByRole(String roleId, List<String> menuIdList) {

        if (StringUtil.isEmpty(menuIdList)) {
            return;
        }
        for (String menuId : menuIdList) {
            if (roleToMenuMapper.insert(new RoleToMenu(roleId, menuId)) <= 0) {
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
    public List<String> listMenuIdByRole(List<String> roleIdList) {
        return super.listMenuIdByRole(roleIdList);
    }

    @Override
    public List<String> listMenuIdByRole(String roleId) {
        return super.listMenuIdByRole(roleId);
    }
}
