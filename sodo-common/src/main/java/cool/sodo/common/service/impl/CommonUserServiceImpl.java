package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonUserMapper;
import cool.sodo.common.service.CommonUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonUserServiceImpl implements CommonUserService {

    public static final String ERROR_SELECT = "用户不存在！";

    public static final String USER_LOGOUT = "用户已注销！";
    public static final String USER_REVIEW = "用户审核中！";
    public static final String USER_FREEZE = "用户已被冻结！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;

    @Resource
    private CommonUserMapper commonUserMapper;

    private LambdaQueryWrapper<User> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                userLambdaQueryWrapper.select(User::getUserId, User::getUsername, User::getPassword
                        , User::getSalt, User::getOpenId, User::getUnionId, User::getClientId, User::getStatus);
                break;
            case SELECT_BASE:
                userLambdaQueryWrapper.select(User::getUserId, User::getNickname, User::getAvatarUrl
                        , User::getDescription, User::getGender, User::getStatus);
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
                .eq(User::getPhone, identity);
        User user = commonUserMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, identity);
        }
        return user;
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#root.args[0]")
    public User getUserByIdentity(String identity) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        userLambdaQueryWrapper.eq(User::getUsername, identity)
                .or()
                .eq(User::getOpenId, identity)
                .or()
                .eq(User::getPhone, identity);
        User user = commonUserMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return user;
    }

    @Override
    public boolean validateUser(String identity) {

        User user = getUserIdentityByIdentity(identity);

        return user.getStatus().equals(Constants.USER_STATUS_NORMAL);
    }

    @Override
    public void checkUserStatus(String identity) {

        User user = getUserIdentityByIdentity(identity);

        switch (user.getStatus()) {
            case Constants.USER_STATUS_LOGOUT:
                throw new SoDoException(ResultEnum.BAD_REQUEST, USER_LOGOUT);
            case Constants.USER_STATUS_REVIEW:
                throw new SoDoException(ResultEnum.BAD_REQUEST, USER_REVIEW);
            case Constants.USER_STATUS_FREEZE:
                throw new SoDoException(ResultEnum.BAD_REQUEST, USER_FREEZE);
            default:
                break;
        }
    }
}
