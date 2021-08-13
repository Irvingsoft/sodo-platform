package cool.sodo.user.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.component.PasswordHelper;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Result;
import cool.sodo.common.service.CommonOauthClientService;
import cool.sodo.common.util.WebUtil;
import cool.sodo.user.entity.PasswordUpdateRequest;
import cool.sodo.user.entity.UserInsertRequest;
import cool.sodo.user.entity.UserRequest;
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

    public static final String ERROR_PRIVATE_KEY = "私钥已失效！";

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

        return Result.success(userService.getUserBase(user.getUserId()));
    }

    @GetMapping(value = "info")
    @ApiOperation(value = "获取当前用户详情信息", notes = "无需参数，根据 Token 获取用户信息")
    public Result getUserGeneral(@CurrentUser User user) {

        return Result.success(userService.getUserGeneral(user.getUserId()));
    }

    @PostMapping(value = "")
    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    public Result insertUser(@RequestBody @Valid UserInsertRequest userInsertRequest, HttpServletRequest request) {

        if (!redisCacheHelper.hasKey(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + WebUtil.getHeader(request, Constants.PASSWORD_KEY))) {
            return Result.badRequest(ERROR_PRIVATE_KEY);
        }
        // 客户端是否允许注册
        oauthClientService.checkOauthClientRegister(request);

        // 验证码校验
        User user = userService.initUser(userInsertRequest, request);
        userService.decryptRsaPassword(user, request);
        passwordHelper.encryptPassword(user);
        userService.insertUser(user);
        return Result.success();
    }

    @PatchMapping(value = "")
    @ApiOperation(value = "修改当前用户基本信息", notes = "修改昵称、头像、性别、学校")
    public Result updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest, @CurrentUser User user) {

        userService.updateUser(userUpdateRequest, user);
        return Result.success();
    }

    @GetMapping(value = "{id}")
    @ApiOperation(value = "查询用户详情信息", notes = "根据 ID 查询用户详情信息")
    public Result getUserInfo(@PathVariable String id) {

        return Result.success(userService.getUserInfo(id));
    }

    @PostMapping(value = "page")
    @ApiOperation(value = "分页查询用户基本信息", notes = "多条件分页查询用户基本信息")
    public Result pageUserBase(@RequestBody UserRequest userRequest) {

        return Result.success(userService.pageUserBase(userRequest));
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
    public Result updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest,
                                 @CurrentUser User user,
                                 HttpServletRequest request) {

        passwordUpdateRequest.setOldPassword(passwordHelper.decryptPassword(request, passwordUpdateRequest.getOldPassword()));
        passwordUpdateRequest.setNewPassword(passwordHelper.decryptPassword(request, passwordUpdateRequest.getNewPassword()));
        userService.updatePassword(passwordUpdateRequest, user);
        return Result.success();
    }
}
