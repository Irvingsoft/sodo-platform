package cool.sodo.common.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.mapper.CommonUserMapper;
import cool.sodo.common.base.service.CommonMenuService;
import cool.sodo.common.base.service.CommonRoleService;
import cool.sodo.common.base.service.CommonUserService;
import cool.sodo.common.base.util.StringUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/1 23:55
 */
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
    public User getBase(String userId) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        userLambdaQueryWrapper.eq(User::getUserId, userId);
        User user = commonUserMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return user;
    }

    @Override
    @Cacheable(cacheNames = Constants.USER_IDENTITY_CACHE_NAME, key = "#identity")
    public User getIdentity(String identity) {

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
        return user;
    }

    @Override
    public void checkUserStatus(User user) {

        switch (user.getStatus()) {
            case Constants.USER_STATUS_LOGOUT:
                throw new SoDoException(ResultEnum.BAD_REQUEST, "用户已注销！");
            case Constants.USER_STATUS_REVIEW:
                throw new SoDoException(ResultEnum.BAD_REQUEST, "用户审核中！");
            case Constants.USER_STATUS_FREEZE:
                throw new SoDoException(ResultEnum.BAD_REQUEST, "用户已被冻结！");
            default:
                break;
        }
    }

    @Override
    public void checkUsername(String userId, String username, String clientId) {

        if (!StringUtil.isEmpty(userId)) {
            User user = getIdentity(userId);
            if (user.getUsername().equals(username)) {
                return;
            }
        }
        if (countByIdentityAndClient(username, clientId) != 0) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, username + " 已被注册！");
        }
    }

    @Override
    public void checkPhone(String userId, String phone, String clientId) {

        if (!StringUtil.isEmpty(userId)) {
            User user = getIdentity(userId);
            if (user.getPhone().equals(phone)) {
                return;
            }
        }
        if (countByIdentityAndClient(phone, clientId) != 0) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, phone + " 已绑定其它账号！");
        }
    }

    @Override
    public void checkEmail(String userId, String email, String clientId) {

        if (!StringUtil.isEmpty(userId)) {
            User user = getIdentity(userId);
            if (user.getEmail().equals(email)) {
                return;
            }
        }
        if (countByIdentityAndClient(email, clientId) != 0) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, email + " 已绑定其它账号！");
        }
    }

    /**
     * 根据身份认证关键字统计用户数量
     *
     * @author TimeChaser
     * @date 2021/8/23 22:13
     */
    private int countByIdentityAndClient(String identity, String clientId) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.eq(User::getClientId, clientId)
                .and(wrapper -> wrapper.eq(User::getUserId, identity)
                        .or()
                        .eq(User::getUsername, identity)
                        .or()
                        .eq(User::getOpenId, identity)
                        .or()
                        .eq(User::getPhone, identity)
                        .or()
                        .eq(User::getEmail, identity));
        return commonUserMapper.selectCount(userLambdaQueryWrapper);
    }
}
