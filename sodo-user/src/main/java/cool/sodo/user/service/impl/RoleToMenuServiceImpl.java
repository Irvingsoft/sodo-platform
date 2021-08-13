package cool.sodo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.RoleToMenu;
import cool.sodo.user.mapper.RoleToMenuMapper;
import cool.sodo.user.service.RoleToMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleToMenuServiceImpl implements RoleToMenuService {

    @Resource
    private RoleToMenuMapper roleToMenuMapper;

    @Override
    public List<RoleToMenu> listRoleToMenuByRole(List<String> roleIdList) {

        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.in(RoleToMenu::getRoleId, roleIdList);
        return roleToMenuMapper.selectList(roleToMenuLambdaQueryWrapper);
    }

    @Override
    public List<String> listRoleToMenuMenuIdByRole(List<String> roleIdList) {

        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.in(RoleToMenu::getRoleId, roleIdList)
                .select(RoleToMenu::getMenuId);
        return roleToMenuMapper.selectList(roleToMenuLambdaQueryWrapper)
                .stream()
                .map(RoleToMenu::getMenuId)
                .collect(Collectors.toList());
    }
}
