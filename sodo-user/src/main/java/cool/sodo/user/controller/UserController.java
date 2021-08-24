package cool.sodo.user.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.component.PasswordHelper;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Result;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.service.CommonOauthClientService;
import cool.sodo.common.util.WebUtil;
import cool.sodo.user.entity.PasswordDTO;
import cool.sodo.user.entity.UserRegister;
import cool.sodo.user.entity.UserUpdateRequest;
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
    public Result insertUser(@RequestBody @Valid UserRegister userRegister, HttpServletRequest request) {

        // 客户端是否允许注册
        OauthClient client = oauthClientService.getOauthClientIdentity(WebUtil.getHeader(request, Constants.CLIENT_ID));
        if (!client.getRegister()) {
            return Result.badRequest("客户端不允许注册！");
        }
        // 用户字段校验
        // 手机验证码校验

        getLock(userRegister);
        userService.insert(userRegister.toUser(), client);
        deleteLock(userRegister);
        return Result.success();
    }

    private void getLock(UserRegister userRegister) {

        long beginAt = System.currentTimeMillis();
        boolean usernameLock = false;
        boolean phoneLock = false;
        while (true) {
            usernameLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userRegister.getUsername(),
                    userRegister.getUsername()) || usernameLock;
            phoneLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userRegister.getPhone(),
                    userRegister.getPhone()) || phoneLock;
            if (usernameLock && phoneLock) {
                return;
            }
            if (System.currentTimeMillis() - beginAt >= Constants.USER_CHECK_LOCK_TIME_OUT_MILLISECONDS) {
                if (!usernameLock) {
                    throw new SoDoException(ResultEnum.SERVER_ERROR, "用户名校验失败！");
                }
                throw new SoDoException(ResultEnum.SERVER_ERROR, "手机号校验失败！");
            }
        }
    }

    private void deleteLock(UserRegister userRegister) {
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userRegister.getUsername());
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userRegister.getPhone());
    }

    @PatchMapping(value = "")
    @ApiOperation(value = "修改当前用户基本信息", notes = "修改昵称、头像、性别、学校")
    public Result updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest, @CurrentUser User user) {

        userService.update(userUpdateRequest, user);
        return Result.success();
    }

    @GetMapping(value = "username/{username}")
    public Result checkUserName(@PathVariable String username) {

        return Result.success(userService.validateUsername(username));
    }

    @GetMapping(value = "phone/{phone}")
    public Result checkPhone(@PathVariable String phone) {

        return Result.success(userService.validatePhone(phone));
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
