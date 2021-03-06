package cool.sodo.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.core.domain.UserToRole;
import cool.sodo.common.core.mapper.CommonUserToRoleMapper;
import cool.sodo.common.core.service.CommonUserToRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TimeChaser
 * @date 2021/9/15 12:08
 */
@Service
public class CommonUserToRoleServiceImpl implements CommonUserToRoleService {

    @Resource
    private CommonUserToRoleMapper commonUserToRoleMapper;

    @Override
    public List<String> listUserToRoleRoleId(String userId) {

        LambdaQueryWrapper<UserToRole> userToRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        userToRoleLambdaQueryWrapper.eq(UserToRole::getUserId, userId)
                .select(UserToRole::getRoleId);
        return commonUserToRoleMapper.selectList(userToRoleLambdaQueryWrapper)
                .stream()
                .map(UserToRole::getRoleId)
                .collect(Collectors.toList());
    }
}
