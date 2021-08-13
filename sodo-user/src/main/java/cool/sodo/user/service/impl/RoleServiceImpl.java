package cool.sodo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.Role;
import cool.sodo.user.mapper.RoleMapper;
import cool.sodo.user.service.RoleService;
import cool.sodo.user.service.UserToRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserToRoleService userToRoleService;

    @Override
    public List<Role> listRole(String userId, String clientId) {

        List<String> roleIdList = userToRoleService.listUserToRoleRoleId(userId);
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleLambdaQueryWrapper.eq(Role::getClientId, clientId).in(Role::getRoleId, roleIdList);
        return roleMapper.selectList(roleLambdaQueryWrapper);
    }

    @Override
    public List<String> listRoleRoleId(String userId, String clientId) {

        List<String> roleIdList = userToRoleService.listUserToRoleRoleId(userId);
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleLambdaQueryWrapper.eq(Role::getClientId, clientId)
                .in(Role::getRoleId, roleIdList)
                .select(Role::getRoleId);
        return roleMapper.selectList(roleLambdaQueryWrapper)
                .stream()
                .map(Role::getRoleId)
                .collect(Collectors.toList());
    }
}
