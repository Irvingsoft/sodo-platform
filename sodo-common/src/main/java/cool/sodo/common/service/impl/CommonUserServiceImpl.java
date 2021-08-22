package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonUserMapper;
import cool.sodo.common.service.CommonMenuService;
import cool.sodo.common.service.CommonRoleService;
import cool.sodo.common.service.CommonUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonUserServiceImpl implements CommonUserService {

    public static final String ERROR_SELECT = "用户不存在！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;

    @Resource
    private CommonUserMapper commonUserMapper;
    @Resource
    private CommonRoleService roleService;
    @Resource
    private CommonMenuService menuService;

    private LambdaQueryWrapper<User> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                userLambdaQueryWrapper.select(User::getUserId, User::getUsername, User::getPassword,
                        User::getSalt, User::getOpenId, User::getUnionId, User::getClientId, User::getPhone,
                        User::getEmail, User::getStatus);
                break;
            case SELECT_BASE:
                userLambdaQueryWrapper.select(User::getUserId, User::getNickname, User::getAvatarUrl
                        , User::getDescription, User::getGender);
                break;
            default:
                break;
        }
        return userLambdaQueryWrapper;
    }

    @Override
    public User getUserBase(String userId) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        userLambdaQueryWrapper.eq(User::getUserId, userId);
        User user = commonUserMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return user;
    }

    @Override
    public User getUserIdentityByIdentity(String identity) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        userLambdaQueryWrapper.eq(User::getUserId, identity)
                .or()
                .eq(User::getUsername, identity)
                .or()
                .eq(User::getOpenId, identity)
                .or()
                .eq(User::getPhone, identity)
                .or()
                .eq(User::getEmail, identity);
        User user = commonUserMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, identity);
        }
        List<String> roleIdList = roleService.listRoleRoleId(user.getUserId());
        user.setCodeList(menuService.listMenuCode(roleIdList));
        return user;
    }

    @Override
    public void checkUserUniqueness(User user) {


    }


}
