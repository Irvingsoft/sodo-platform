package cool.sodo.housekeeper.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Result;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.housekeeper.entity.GrantDTO;
import cool.sodo.housekeeper.entity.UserDTO;
import cool.sodo.housekeeper.entity.UserInsertDTO;
import cool.sodo.housekeeper.entity.UserUpdateDTO;
import cool.sodo.housekeeper.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    @PostMapping(value = "")
    public Result insertUser(@RequestBody UserInsertDTO userInsertDTO, @CurrentUser User currentUser) {

        // 用户字段校验

        getLock(userInsertDTO);
        userService.insert(userInsertDTO.toUser(), currentUser.getUserId());
        deleteLock(userInsertDTO);
        return Result.success();
    }

    private void getLock(UserInsertDTO userInsertDTO) {

        long beginAt = System.currentTimeMillis();
        boolean usernameLock = false;
        boolean phoneLock = false;
        boolean emailLock = false;
        while (true) {
            usernameLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userInsertDTO.getUsername(),
                    userInsertDTO.getUsername()) || usernameLock;
            phoneLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userInsertDTO.getPhone(),
                    userInsertDTO.getPhone()) || phoneLock;
            emailLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userInsertDTO.getEmail(),
                    userInsertDTO.getEmail()) || emailLock;
            if (usernameLock && phoneLock && emailLock) {
                return;
            }
            if (System.currentTimeMillis() - beginAt >= Constants.USER_CHECK_LOCK_TIME_OUT_MILLISECONDS) {
                deleteLock(userInsertDTO);
                if (!usernameLock) {
                    throw new SoDoException(ResultEnum.SERVER_ERROR, "用户名校验失败！");
                }
                if (!phoneLock) {
                    throw new SoDoException(ResultEnum.SERVER_ERROR, "手机号校验失败！");
                }
                throw new SoDoException(ResultEnum.SERVER_ERROR, "邮箱校验失败！");
            }
        }
    }

    private void deleteLock(UserInsertDTO userInsertDTO) {
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userInsertDTO.getUsername());
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userInsertDTO.getPhone());
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userInsertDTO.getEmail());
    }

    @DeleteMapping(value = "{userId}")
    public Result deleteUser(@PathVariable String userId, @CurrentUser User user) {

        userService.delete(userId, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "")
    public Result deleteUserList(@RequestBody List<String> userIdList, @CurrentUser User user) {

        userService.delete(userIdList, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @CurrentUser User currentUser) {

        getLock(userUpdateDTO);
        userService.update(userUpdateDTO.toUser(), currentUser.getUserId());
        deleteLock(userUpdateDTO);
        return Result.success();
    }

    private void getLock(UserUpdateDTO userUpdateDTO) {

        long beginAt = System.currentTimeMillis();
        boolean phoneLock = false;
        boolean emailLock = false;
        while (true) {
            phoneLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userUpdateDTO.getPhone(),
                    userUpdateDTO.getPhone()) || phoneLock;
            emailLock = redisCacheHelper.setIfAbsent(
                    Constants.USER_CHECK_LOCK_PREFIX + userUpdateDTO.getEmail(),
                    userUpdateDTO.getEmail()) || emailLock;
            if (phoneLock && emailLock) {
                return;
            }
            if (System.currentTimeMillis() - beginAt >= Constants.USER_CHECK_LOCK_TIME_OUT_MILLISECONDS) {
                deleteLock(userUpdateDTO);
                if (!phoneLock) {
                    throw new SoDoException(ResultEnum.SERVER_ERROR, "手机号校验失败！");
                }
                throw new SoDoException(ResultEnum.SERVER_ERROR, "邮箱校验失败！");
            }
        }
    }

    private void deleteLock(UserUpdateDTO userUpdateDTO) {
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userUpdateDTO.getPhone());
        redisCacheHelper.delete(Constants.USER_CHECK_LOCK_PREFIX + userUpdateDTO.getEmail());
    }

    @PostMapping(value = "grant")
    public Result grant(@RequestBody GrantDTO grantDTO) {

        userService.grant(grantDTO.getUserIdList(), grantDTO.getRoleIdList());
        return Result.success();
    }

    @PostMapping(value = "page")
    public Result pageUserBaseDetail(@RequestBody @Valid UserDTO userDTO) {
        return Result.success(userService.pageBaseDetail(userDTO));
    }

    @GetMapping(value = "{userId}")
    public Result getUserInfoDetail(@PathVariable String userId) {
        return Result.success(userService.getInfoDetail(userId));
    }
}
