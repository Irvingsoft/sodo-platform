package cool.sodo.housekeeper.controller;

import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.core.annotation.CurrentUser;
import cool.sodo.housekeeper.entity.GrantDTO;
import cool.sodo.housekeeper.entity.UserDTO;
import cool.sodo.housekeeper.entity.UserInsertDTO;
import cool.sodo.housekeeper.entity.UserUpdateDTO;
import cool.sodo.housekeeper.service.UserService;
import cool.sodo.redis.annotation.Lock;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping(value = "")
    @Lock(key = "#userInsertDTO.username")
    @Lock(key = "#userInsertDTO.phone")
    @Lock(key = "#userInsertDTO.email")
    public Result insertUser(@RequestBody UserInsertDTO userInsertDTO, @CurrentUser User currentUser) {

        // 用户字段校验
        userService.insert(userInsertDTO.toUser(), currentUser.getUserId());
        return Result.success();
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
    @Lock(key = "#userUpdateDTO.phone")
    @Lock(key = "#userUpdateDTO.email")
    public Result updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @CurrentUser User currentUser) {

        userService.update(userUpdateDTO.toUser(), currentUser.getUserId());
        return Result.success();
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

    @GetMapping(value = "logout/{userId}")
    public Result logout(@PathVariable String userId) {
        userService.logout(userId);
        return Result.success();
    }
}
