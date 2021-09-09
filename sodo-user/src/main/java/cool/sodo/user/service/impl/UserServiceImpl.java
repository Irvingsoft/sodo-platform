package cool.sodo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.domain.OauthClient;
import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.AsyncException;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.mapper.CommonUserMapper;
import cool.sodo.common.base.service.impl.CommonUserServiceImpl;
import cool.sodo.common.base.util.RsaUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.component.PasswordHelper;
import cool.sodo.user.entity.PasswordDTO;
import cool.sodo.user.entity.UserUpdateDTO;
import cool.sodo.user.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * User 接口实现
 *
 * @author TimeChaser
 * @date 2021/7/3 15:13
 */
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends CommonUserServiceImpl implements UserService {

    public static final String ERROR_UPDATE = "用户信息更新失败！ UserId：";
    public static final String ERROR_SELECT = "用户不存在！";
    public static final String ERROR_COUNT = "用户已存在！";
    public static final String ERROR_INSERT = "新增用户失败！";
    public static final String ERROR_INSERT_MQ = "异步消息队列新增用户失败！";

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

    private LambdaQueryWrapper<User> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_BASE:
                userLambdaQueryWrapper.select(User::getUserId, User::getName, User::getNickname, User::getAvatarUrl,
                        User::getDescription, User::getUsername, User::getGender, User::getStatus, User::getLoginAt);
                break;
            case SELECT_GENERAL:
                userLambdaQueryWrapper.select(User::getUserId, User::getName, User::getNickname, User::getAvatarUrl,
                        User::getDescription, User::getUsername, User::getClientId, User::getSchoolId,
                        User::getPhone, User::getGender, User::getCountry, User::getProvince, User::getCity,
                        User::getStatus, User::getLoginAt);
                break;
            case SELECT_INFO:
                userLambdaQueryWrapper.select(User::getUserId, User::getName, User::getNickname, User::getDescription, User::getAvatarUrl,
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
    public void insert(User user, OauthClient client) {

        decryptRsaPassword(user);
        passwordHelper.encryptPassword(user);
        user.init(client);
        user.setNickname(Constants.NICKNAME_PREFIX + userMapper.selectCount(null));
        checkUsername(null, user.getUsername(), user.getClientId());
        checkPhone(null, user.getPhone(), user.getClientId());
        if (userMapper.insert(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT, user);
        }
    }

    @Override
    @Async
    public void insertFromMq(User user) {

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
    public void update(UserUpdateDTO userUpdateDTO, User user) {

        if (!StringUtil.isEmpty(userUpdateDTO.getName())) {
            user.setName(userUpdateDTO.getName());
        }
        if (!StringUtil.isEmpty(userUpdateDTO.getNickname())) {
            user.setNickname(userUpdateDTO.getNickname());
        }
        if (!StringUtil.isEmpty(userUpdateDTO.getAvatarUrl())) {
            user.setAvatarUrl(userUpdateDTO.getAvatarUrl());
        }
        if (!StringUtil.isEmpty(userUpdateDTO.getGender())) {
            user.setGender(userUpdateDTO.getGender());
        }
        if (!StringUtil.isEmpty(userUpdateDTO.getSchoolId())) {
            user.setSchoolId(userUpdateDTO.getSchoolId());
        }
        if (userMapper.updateById(user) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, user);
        }
    }

    @Override
    public void updatePassword(@Valid PasswordDTO passwordDTO, User user) {

        if (!passwordHelper.validatePassword(user, passwordDTO.getOldPassword())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "原密码有误！", user);
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
    public User getBase(String id) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        userLambdaQueryWrapper.eq(User::getUserId, id);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return user;
    }

    @Override
    public User getGeneral(String id) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_GENERAL);
        userLambdaQueryWrapper.eq(User::getUserId, id);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
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

    @Override
    public void checkUsername(String userId, String username, String clientId) {
        super.checkUsername(userId, username, clientId);
    }

    @Override
    public void checkPhone(String userId, String phone, String clientId) {
        super.checkPhone(userId, phone, clientId);
    }
}
