package cool.sodo.user.controller;

import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.domain.OauthClient;
import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.service.CommonOauthClientService;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.annotation.CurrentUser;
import cool.sodo.common.core.component.PasswordHelper;
import cool.sodo.user.entity.PasswordDTO;
import cool.sodo.user.entity.UserRegisterDTO;
import cool.sodo.user.entity.UserUpdateDTO;
import cool.sodo.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 用户接口
 *
 * @author TimeChaser
 * @date 2021/7/27 12:47
 */
@RestController
@RequestMapping(value = "user")
public class UserController {


    @Resource
    private UserService userService;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private PasswordHelper passwordHelper;
    @Resource
    private CommonOauthClientService oauthClientService;

    @GetMapping(value = "")
    @ApiOperation(value = "获取当前用户基本信息", notes = "登录成功后获取用户基本信息")
    public Result getUserBase(@CurrentUser User user) {

        return Result.success(userService.getBase(user.getUserId()));
    }

    @GetMapping(value = "info")
    @ApiOperation(value = "获取当前用户详情信息", notes = "无需参数，根据 Token 获取用户信息")
    public Result getUserGeneral(@CurrentUser User user) {

        return Result.success(userService.getGeneral(user.getUserId()));
    }

    @PostMapping(value = "")
    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    public Result insertUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO, HttpServletRequest request) {

        // 客户端是否允许注册
        OauthClient client = oauthClientService.getOauthClientIdentity(WebUtil.getHeader(request, Constants.CLIENT_ID));
        if (!client.getRegister()) {
            return Result.badRequest("客户端不允许注册！");
        }
        // 手机验证码校验

        getLock(userRegisterDTO);
        userService.insert(userRegisterDTO.toUser(), client);
        deleteLock(userRegisterDTO);
        return Result.success();
    }

    private void getLock(UserRegisterDTO userRegisterDTO) {

        long beginAt = System.currentTimeMillis();
        boolean usernameLock = false;
        boolean phoneLock = false;
        while (true) {
            usernameLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userRegisterDTO.getUsername(),
                    userRegisterDTO.getUsername(),
                    Constants.USER_CHECK_LOCK_EXPIRE_SECONDS) || usernameLock;
            phoneLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userRegisterDTO.getPhone(),
                    userRegisterDTO.getPhone(),
                    Constants.USER_CHECK_LOCK_EXPIRE_SECONDS) || phoneLock;
            if (usernameLock && phoneLock) {
                return;
            }
            if (System.currentTimeMillis() - beginAt >= Constants.USER_CHECK_LOCK_TIME_OUT_MILLISECONDS) {
                deleteLock(userRegisterDTO);
                if (!usernameLock) {
                    throw new SoDoException(ResultEnum.SERVER_ERROR, "用户名校验失败！");
                }
                throw new SoDoException(ResultEnum.SERVER_ERROR, "手机号校验失败！");
            }
        }
    }

    private void deleteLock(UserRegisterDTO userRegisterDTO) {
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userRegisterDTO.getUsername());
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userRegisterDTO.getPhone());
    }

    @PatchMapping(value = "")
    @ApiOperation(value = "修改当前用户基本信息", notes = "修改昵称、头像、性别、学校")
    public Result updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO, @CurrentUser User user) {

        userService.update(userUpdateDTO, user);
        return Result.success();
    }

    @GetMapping(value = "username/{username}")
    public Result checkUserName(@PathVariable String username, HttpServletRequest request) {

        userService.checkUsername(null,
                username,
                WebUtil.getHeader(request, Constants.CLIENT_ID));
        return Result.success();
    }

    @GetMapping(value = "phone/{phone}")
    public Result checkPhone(@PathVariable String phone, HttpServletRequest request) {

        userService.checkPhone(null,
                phone,
                WebUtil.getHeader(request, Constants.CLIENT_ID));
        return Result.success();
    }

    @PostMapping(value = "password")
    public Result updatePassword(@RequestBody PasswordDTO passwordDTO,
                                 @CurrentUser User user,
                                 HttpServletRequest request) {

        passwordDTO.setOldPassword(passwordHelper.decryptPassword(request, passwordDTO.getOldPassword()));
        passwordDTO.setNewPassword(passwordHelper.decryptPassword(request, passwordDTO.getNewPassword()));
        userService.updatePassword(passwordDTO, user);
        return Result.success();
    }
}
