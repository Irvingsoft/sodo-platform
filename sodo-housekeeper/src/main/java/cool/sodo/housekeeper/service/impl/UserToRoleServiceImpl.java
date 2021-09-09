package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.domain.UserToRole;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.mapper.CommonUserToRoleMapper;
import cool.sodo.common.base.service.impl.CommonUserToRoleServiceImpl;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.housekeeper.service.UserToRoleService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Primary
public class UserToRoleServiceImpl extends CommonUserToRoleServiceImpl implements UserToRoleService {

    @Resource
    private CommonUserToRoleMapper userToRoleMapper;

    @Override
    public void insertByUser(String userId, List<String> roleIdList) {

        if (StringUtil.isEmpty(roleIdList)) {
            return;
        }
        for (String roleId : roleIdList) {
            if (userToRoleMapper.insert(new UserToRole(userId, roleId)) <= 0) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "新增 UserToRole 失败！");
            }
        }
    }

    @Override
    public void deleteByUser(String userId) {

        LambdaQueryWrapper<UserToRole> userToRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        userToRoleLambdaQueryWrapper.eq(UserToRole::getUserId, userId);
        if (userToRoleMapper.delete(userToRoleLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 UserToRole 记录失败！");
        }
    }

    @Override
    public void deleteByUser(List<String> userIdList) {

        if (StringUtil.isEmpty(userIdList)) {
            return;
        }
        LambdaQueryWrapper<UserToRole> userToRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        userToRoleLambdaQueryWrapper.in(UserToRole::getUserId, userIdList);
        if (userToRoleMapper.delete(userToRoleLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 UserToRole 记录失败！");
        }
    }

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
