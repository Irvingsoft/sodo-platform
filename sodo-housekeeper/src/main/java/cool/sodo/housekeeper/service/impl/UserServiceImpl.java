package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.component.PasswordHelper;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonUserMapper;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.entity.UserDTO;
import cool.sodo.housekeeper.service.UserService;
import cool.sodo.housekeeper.service.UserToRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public static final int SELECT_BASE = 0;
    public static final int SELECT_INFO = 1;

    @Resource
    private CommonUserMapper userMapper;
    @Resource
    private PasswordHelper passwordHelper;
    @Resource
    private UserToRoleService userToRoleService;
    @Resource
    private CommonUserService commonUserService;

    private LambdaQueryWrapper<User> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_BASE:
                userLambdaQueryWrapper.select(User::getUserId, User::getNickname, User::getDescription, User::getUsername, User::getOpenId,
                        User::getPhone, User::getGender, User::getStatus, User::getCreateAt, User::getUpdateAt);
                break;
            case SELECT_INFO:
                userLambdaQueryWrapper.select(User::getUserId, User::getNickname, User::getDescription, User::getUsername, User::getOpenId,
                        User::getPhone, User::getGender, User::getCountry, User::getProvince, User::getCity, User::getStatus,
                        User::getLoginAt, User::getLoginIp, User::getCreateAt, User::getUpdateAt);
                break;
            default:
                break;
        }
        return userLambdaQueryWrapper;
    }

    @Override
    public void insert(User user, String userId) {

        user.init(userId);
        passwordHelper.encryptPassword(user);
        if (userMapper.insert(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "新增 User 记录失败！");
        }
    }

    @Override
    public void delete(String userId, String deleteBy) {

        User user = get(userId);
        user.delete(deleteBy);
        update(user);
        if (userMapper.deleteById(userId) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "删除 User 记录失败！");
        }
    }

    @Override
    public void update(User user) {
        if (userMapper.updateById(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "更新 User 记录失败！");
        }
    }

    @Override
    public void update(User user, String updateBy) {

        User userOld = get(user.getUserId());
        userOld.update(user, updateBy);
        passwordHelper.encryptPassword(user);
        if (userMapper.updateById(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, "更新 User 记录失败！");
        }
        if (!StringUtil.isEmpty(user.getRoleIdList())) {
            userToRoleService.deleteByUser(user.getUserId());
            userToRoleService.insertByUser(user.getUserId(), user.getRoleIdList());
        }
    }

    @Override
    public void grant(List<String> userIdList, List<String> roleIdList) {

        if (StringUtil.isEmpty(userIdList)) {
            return;
        }
        userToRoleService.deleteByUser(userIdList);
        for (String userId : userIdList) {
            userToRoleService.insertByUser(userId, roleIdList);
        }
    }

    @Override
    public User get(String userId) {

        User user = userMapper.selectById(userId);
        if (StringUtil.isEmpty(user)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "用户不存在！");
        }
        return user;
    }

    @Override
    public User getInfoDetail(String userId) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        userLambdaQueryWrapper.eq(User::getUserId, userId);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (StringUtil.isEmpty(user)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "用户不存在！");
        }
        user.setRoleIdList(userToRoleService.listUserToRoleRoleId(userId));
        return user;
    }

    @Override
    public IPage<User> pageBaseDetail(UserDTO userDTO) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        userLambdaQueryWrapper.eq(User::getClientId, userDTO.getClientId());
        if (!StringUtil.isEmpty(userDTO.getStatus())) {
            userLambdaQueryWrapper.eq(User::getStatus, userDTO.getStatus());
        }
        if (!StringUtil.isEmpty(userDTO.getIdentity())) {
            userLambdaQueryWrapper.and(wrapper -> wrapper.eq(User::getUserId, userDTO.getIdentity())
                    .eq(User::getUsername, userDTO.getIdentity())
                    .or()
                    .eq(User::getOpenId, userDTO.getIdentity())
                    .or()
                    .eq(User::getPhone, userDTO.getIdentity()));
        }
        if (!StringUtil.isEmpty(userDTO.getContent())) {
            userLambdaQueryWrapper.and(wrapper -> wrapper.eq(User::getNickname, userDTO.getContent())
                    .eq(User::getDescription, userDTO.getContent()));
        }

        Page<User> userPage = userMapper.selectPage(new Page<>(userDTO.getPageNum(), userDTO.getPageSize()), userLambdaQueryWrapper);
        userPage.getRecords().forEach(user -> user.setRoleIdList(userToRoleService.listUserToRoleRoleId(user.getUserId())));
        return userPage;
    }
}
