package cool.sodo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.component.PasswordHelper;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonUserMapper;
import cool.sodo.common.service.CommonOauthClientService;
import cool.sodo.common.util.RsaUtil;
import cool.sodo.common.util.StringUtil;
import cool.sodo.common.util.WebUtil;
import cool.sodo.user.entity.PasswordDTO;
import cool.sodo.user.entity.UserInsertRequest;
import cool.sodo.user.entity.UserUpdateRequest;
import cool.sodo.user.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

    public static final String ERROR_OLD_PASSWORD = "原密码有误！";
    public static final String ERROR_NEW_PASSWORD = "原密码有误！";

    public static final String ERROR_PRIVATE_KEY = "私钥已失效！";

    public static final int SELECT_BASE = 0;
    public static final int SELECT_GENERAL = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private CommonUserMapper userMapper;
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
    public synchronized void insertUser(User user, OauthClient client) {

        decryptRsaPassword(user);
        passwordHelper.encryptPassword(user);

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

    @Override
    @Async
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
    public void updatePassword(PasswordDTO passwordDTO, User user) {

        if (StringUtil.isEmpty(passwordDTO.getOldPassword()) ||
                !passwordHelper.validatePassword(user, passwordDTO.getOldPassword())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_OLD_PASSWORD, user);
        }
        if (StringUtil.isEmpty(passwordDTO.getNewPassword()) ||
                !validatePassword(passwordDTO.getNewPassword())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_NEW_PASSWORD, user);
        }
        user.updatePassword(passwordDTO.getNewPassword());
        passwordHelper.encryptPassword(user);
        if (userMapper.updateById(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, user);
        }
    }

    @Override
    @Async
    public void updateUserLogin(String identity, String loginIp) {

        // TODO IP 距离检查，发送异常登录消息
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        userLambdaUpdateWrapper.eq(User::getUserId, identity)
                .or()
                .eq(User::getUsername, identity)
                .or()
                .eq(User::getOpenId, identity)
                .set(User::getLoginAt, new Date())
                .set(User::getLoginIp, loginIp);
        if (userMapper.update(null, userLambdaUpdateWrapper) <= 0) {
            throw new AsyncException("更新用户登陆时间失败！");
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
        user.init(oauthClientService.getOauthClientIdentity(clientId));
        return user;
    }

    @Override
    public void decryptRsaPassword(User user) {

        HttpServletRequest request = WebUtil.getRequest();
        String privateKeyCacheKey = Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + WebUtil.getHeader(request, Constants.PASSWORD_KEY);
        if (!redisCacheHelper.hasKey(privateKeyCacheKey)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_PRIVATE_KEY);
        }
        String privateKey = (String) redisCacheHelper.get(privateKeyCacheKey);
        user.setPassword(RsaUtil.decryptByPrivateKey(user.getPassword(), privateKey));
    }
}
