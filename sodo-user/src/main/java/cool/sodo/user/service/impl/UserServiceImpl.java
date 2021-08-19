package cool.sodo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.component.PasswordHelper;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.service.CommonOauthClientService;
import cool.sodo.common.util.RsaUtil;
import cool.sodo.common.util.StringUtil;
import cool.sodo.common.util.WebUtil;
import cool.sodo.user.entity.PasswordUpdateRequest;
import cool.sodo.user.entity.UserInsertRequest;
import cool.sodo.user.entity.UserRequest;
import cool.sodo.user.entity.UserUpdateRequest;
import cool.sodo.user.mapper.UserMapper;
import cool.sodo.user.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * User 接口实现
 *
 * @author TimeChaser
 * @date 2021/7/3 15:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    public static final String ERROR_UPDATE = "用户信息更新失败！ UserId：";
    public static final String ERROR_SELECT = "用户不存在！";
    public static final String ERROR_COUNT = "用户已存在！";
    public static final String ERROR_INSERT = "新增用户失败！";
    public static final String ERROR_INSERT_MQ = "异步消息队列新增用户失败！";

    public static final String ERROR_USERNAME = "用户名无效！";
    public static final String ERROR_PHONE = "手机号无效！";
    public static final String ERROR_PASSWORD = "密码无效！";

    public static final String ERROR_OLDPASSWORD = "原密码有误！";
    public static final String ERROR_NEWPASSWORD = "原密码有误！";

    public static final int SELECT_BASE = 0;
    public static final int SELECT_GENERAL = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private PasswordHelper passwordHelper;
    @Resource
    private CommonOauthClientService oauthClientService;

    private LambdaQueryWrapper<User> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_BASE:
                userLambdaQueryWrapper.select(User::getUserId, User::getNickname, User::getAvatarUrl,
                        User::getDescription, User::getUsername, User::getGender, User::getStatus, User::getLoginAt);
                break;
            case SELECT_GENERAL:
                userLambdaQueryWrapper.select(User::getUserId, User::getNickname, User::getAvatarUrl,
                        User::getDescription, User::getUsername, User::getClientId, User::getSchoolId,
                        User::getPhone, User::getGender, User::getCountry, User::getProvince, User::getCity,
                        User::getStatus, User::getLoginAt);
                break;
            case SELECT_INFO:
                userLambdaQueryWrapper.select(User::getUserId, User::getNickname, User::getDescription, User::getAvatarUrl,
                        User::getUsername, User::getOpenId, User::getUnionId, User::getClientId, User::getSchoolId,
                        User::getPhone, User::getGender, User::getCountry, User::getProvince, User::getCity, User::getStatus,
                        User::getCreateAt, User::getUpdateAt, User::getLoginAt);
                break;
            default:
                break;
        }
        return userLambdaQueryWrapper;
    }

    @Override
    public synchronized void insertUser(User user) {

        user.setNickname(Constants.NICKNAME_PREFIX + userMapper.selectCount(null));
        if (StringUtil.isEmpty(user.getUsername()) || !validateUsername(user.getUsername())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_USERNAME, user.getClientId());
        }
        if (StringUtil.isEmpty(user.getPhone()) || !validatePhone(user.getPhone())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_PHONE, user.getClientId());
        }
        if (StringUtil.isEmpty(user.getPassword()) || !validatePassword(user.getPassword())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_PASSWORD, user.getClientId());
        }
        if (userMapper.insert(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT, user);
        }
    }

    @Async
    @Override
    public void insertUserMq(User user) {

        LambdaQueryWrapper<User> queryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        queryWrapper.eq(User::getOpenId, user.getOpenId());
        if (userMapper.selectCount(queryWrapper) != 0) {
            throw new AsyncException(ERROR_COUNT);
        }
        if (userMapper.insert(user) <= 0) {
            throw new AsyncException(ERROR_INSERT_MQ);
        }
    }

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest, User user) {

        if (!StringUtil.isEmpty(userUpdateRequest.getNickname())) {
            user.setNickname(userUpdateRequest.getNickname());
        }
        if (!StringUtil.isEmpty(userUpdateRequest.getAvatarUrl())) {
            user.setAvatarUrl(userUpdateRequest.getAvatarUrl());
        }
        if (!StringUtil.isEmpty(userUpdateRequest.getGender())) {
            user.setGender(userUpdateRequest.getGender());
        }
        if (!StringUtil.isEmpty(userUpdateRequest.getSchoolId())) {
            user.setSchoolId(userUpdateRequest.getSchoolId());
        }
        if (userMapper.updateById(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, user);
        }
    }

    @Override
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest, User user) {

        if (StringUtil.isEmpty(passwordUpdateRequest.getOldPassword()) ||
                !passwordHelper.validatePassword(user, passwordUpdateRequest.getOldPassword())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_OLDPASSWORD, user);
        }
        if (StringUtil.isEmpty(passwordUpdateRequest.getNewPassword()) ||
                !validatePassword(passwordUpdateRequest.getNewPassword())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_NEWPASSWORD, user);
        }
        user.setPassword(passwordUpdateRequest.getNewPassword());
        passwordHelper.encryptPassword(user);
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        userLambdaUpdateWrapper.set(User::getSalt, user.getSalt())
                .set(User::getPassword, user.getPassword());
        if (userMapper.update(null, userLambdaUpdateWrapper) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, user);
        }
    }

    @Override
    public User getUserBase(String id) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        userLambdaQueryWrapper.eq(User::getUserId, id);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return user;
    }

    @Override
    public User getUserGeneral(String id) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_GENERAL);
        userLambdaQueryWrapper.eq(User::getUserId, id);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return user;
    }

    @Override
    public User getUserInfo(String id) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        userLambdaQueryWrapper.eq(User::getUserId, id);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return user;
    }

    @Override
    public IPage<User> pageUserBase(UserRequest userRequest) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);

        if (!StringUtil.isEmpty(userRequest.getClientId())) {
            userLambdaQueryWrapper.eq(User::getClientId, userRequest.getClientId());
        }
        if (!StringUtil.isEmpty(userRequest.getGender())) {
            userLambdaQueryWrapper.eq(User::getGender, userRequest.getGender());
        }
        if (!StringUtil.isEmpty(userRequest.getStatus())) {
            userLambdaQueryWrapper.eq(User::getStatus, userRequest.getStatus());
        }
        if (!StringUtil.isEmpty(userRequest.getProvince())) {
            userLambdaQueryWrapper.eq(User::getProvince, userRequest.getProvince());
        }
        if (!StringUtil.isEmpty(userRequest.getCity())) {
            userLambdaQueryWrapper.eq(User::getCity, userRequest.getCity());
        }
        if (!StringUtil.isEmpty(userRequest.getIdentity())) {
            userLambdaQueryWrapper.and(wrapper -> wrapper.eq(User::getUsername, userRequest.getIdentity())
                    .or()
                    .eq(User::getOpenId, userRequest.getIdentity())
                    .or()
                    .eq(User::getPhone, userRequest.getIdentity()));
        }
        if (!StringUtil.isEmpty(userRequest.getContent())) {
            userLambdaQueryWrapper.and(wrapper -> wrapper.eq(User::getNickname, userRequest.getContent())
                    .or()
                    .eq(User::getDescription, userRequest.getContent()));
        }

        return userMapper.selectPage(new Page<>(userRequest.getPageNum(), userRequest.getPageSize()), userLambdaQueryWrapper);
    }

    @Override
    public boolean validateUsername(String username) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        return userMapper.selectCount(userLambdaQueryWrapper) == 0 && Pattern.matches(Constants.USERNAME_REGEX, username);
    }

    @Override
    public boolean validatePhone(String phone) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.eq(User::getPhone, phone);
        return userMapper.selectCount(userLambdaQueryWrapper) == 0 && Pattern.matches(Constants.PHONE_REGEX, phone);
    }

    @Override
    public boolean validatePassword(String password) {

//        return Pattern.matches(Constants.PASSWORD_REGEX, password);
        return password.length() >= 6 && password.length() <= 20;
    }

    @Override
    public User initUser(UserInsertRequest userInsertRequest, HttpServletRequest request) {

        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        User user = userInsertRequest.toUser();
        OauthClient oauthClient = oauthClientService.getOauthClientIdentity(clientId);
        user.init();
        user.setClientId(oauthClient.getClientId());
        user.setStatus(oauthClient.getUserStatus());
        return user;
    }

    @Override
    public void decryptRsaPassword(User user, HttpServletRequest request) {

        String privateKey = (String) redisCacheHelper.get(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + WebUtil.getHeader(request, Constants.PASSWORD_KEY));
        user.setPassword(RsaUtil.decryptByPrivateKey(user.getPassword(), privateKey));
    }
}