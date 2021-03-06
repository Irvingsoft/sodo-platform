package cool.sodo.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.domain.RoleToMenu;
import cool.sodo.common.core.mapper.CommonRoleToMenuMapper;
import cool.sodo.common.core.service.CommonRoleToMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TimeChaser
 * @date 2021/9/15 12:07
 */
@Service
public class CommonRoleToMenuServiceImpl implements CommonRoleToMenuService {

    @Resource
    private CommonRoleToMenuMapper commonRoleToMenuMapper;

    @Override
    public List<String> listMenuIdByRole(List<String> roleIdList) {

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
    public List<String> listMenuIdByRole(String roleId) {

        LambdaQueryWrapper<RoleToMenu> roleToMenuLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleToMenuLambdaQueryWrapper.select(RoleToMenu::getMenuId)
                .eq(RoleToMenu::getRoleId, roleId);
        return commonRoleToMenuMapper.selectList(roleToMenuLambdaQueryWrapper)
                .stream()
                .map(RoleToMenu::getMenuId)
                .collect(Collectors.toList());
    }
}
