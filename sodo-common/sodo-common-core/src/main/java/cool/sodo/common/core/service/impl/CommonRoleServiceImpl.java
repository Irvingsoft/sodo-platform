package cool.sodo.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.domain.Role;
import cool.sodo.common.core.mapper.CommonRoleMapper;
import cool.sodo.common.core.service.CommonRoleService;
import cool.sodo.common.core.service.CommonUserToRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TimeChaser
 * @date 2021/9/2 0:04
 */
@Service
public class CommonRoleServiceImpl implements CommonRoleService {

    @Resource
    private CommonRoleMapper commonRoleMapper;
    @Resource
    private CommonUserToRoleService userToRoleService;

    @Override
    public List<String> listRoleId(String userId) {

        List<String> roleIdList = userToRoleService.listUserToRoleRoleId(userId);
        if (StringUtil.isEmpty(roleIdList)) {
            return null;
        }
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = Wrappers.lambdaQuery();
        roleLambdaQueryWrapper.in(Role::getRoleId, roleIdList)
                .select(Role::getRoleId);
        return commonRoleMapper.selectList(roleLambdaQueryWrapper)
                .stream()
                .map(Role::getRoleId)
                .collect(Collectors.toList());
    }
}
