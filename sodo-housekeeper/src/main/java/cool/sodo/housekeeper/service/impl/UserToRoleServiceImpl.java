package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.UserToRole;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonUserToRoleMapper;
import cool.sodo.common.service.impl.CommonUserToRoleServiceImpl;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.service.UserToRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserToRoleServiceImpl extends CommonUserToRoleServiceImpl implements UserToRoleService {

    @Resource
    private CommonUserToRoleMapper userToRoleMapper;

    @Override
    public void deleteByRole(String roleId) {

        LambdaQueryWrapper<UserToRole> userToRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        userToRoleLambdaQueryWrapper.eq(UserToRole::getRoleId, roleId);
        if (userToRoleMapper.delete(userToRoleLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 UserToRole 记录失败！");
        }
    }

    @Override
    public void deleteByRole(List<String> roleIdList) {

        if (StringUtil.isEmpty(roleIdList)) {
            return;
        }
        LambdaQueryWrapper<UserToRole> userToRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        userToRoleLambdaQueryWrapper.in(UserToRole::getRoleId, roleIdList);
        if (userToRoleMapper.delete(userToRoleLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 UserToRole 记录失败！");
        }
    }

    @Override
    public List<String> listUserToRoleRoleId(String userId) {
        return super.listUserToRoleRoleId(userId);
    }
}
