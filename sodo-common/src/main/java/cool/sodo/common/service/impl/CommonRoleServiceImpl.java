package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.Role;
import cool.sodo.common.mapper.CommonRoleMapper;
import cool.sodo.common.service.CommonRoleService;
import cool.sodo.common.service.CommonUserToRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonRoleServiceImpl implements CommonRoleService {

    @Resource
    private CommonRoleMapper commonRoleMapper;
    @Resource
    private CommonUserToRoleService userToRoleService;

    @Override
    public List<String> listRoleRoleId(String userId) {

        List<String> roleIdList = userToRoleService.listUserToRoleRoleId(userId);
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleLambdaQueryWrapper.in(Role::getRoleId, roleIdList)
                .select(Role::getRoleId);
        return commonRoleMapper.selectList(roleLambdaQueryWrapper)
                .stream()
                .map(Role::getRoleId)
                .collect(Collectors.toList());
    }
}
