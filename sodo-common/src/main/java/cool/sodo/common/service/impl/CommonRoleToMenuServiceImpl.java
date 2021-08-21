package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.RoleToMenu;
import cool.sodo.common.mapper.CommonRoleToMenuMapper;
import cool.sodo.common.service.CommonRoleToMenuService;
import cool.sodo.common.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonRoleToMenuServiceImpl implements CommonRoleToMenuService {

    @Resource
    private CommonRoleToMenuMapper commonRoleToMenuMapper;

    @Override
    public List<String> listRoleToMenuMenuIdByRole(List<String> roleIdList) {

        if (StringUtil.isEmpty(roleIdList)) {
            return null;
        }
        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.select(RoleToMenu::getMenuId)
                .in(RoleToMenu::getRoleId, roleIdList);
        return commonRoleToMenuMapper.selectList(roleToMenuLambdaQueryWrapper)
                .stream()
                .map(RoleToMenu::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listRoleToMenuMenuIdByRole(String roleId) {

        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.select(RoleToMenu::getMenuId)
                .eq(RoleToMenu::getRoleId, roleId);
        return commonRoleToMenuMapper.selectList(roleToMenuLambdaQueryWrapper)
                .stream()
                .map(RoleToMenu::getMenuId)
                .collect(Collectors.toList());
    }
}
