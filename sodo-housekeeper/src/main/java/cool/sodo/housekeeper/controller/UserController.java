package cool.sodo.housekeeper.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Result;
import cool.sodo.housekeeper.entity.GrantDTO;
import cool.sodo.housekeeper.entity.UserDTO;
import cool.sodo.housekeeper.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    @PostMapping(value = "")
    public Result insertUser(@RequestBody User user, @CurrentUser User currentUser) {

        // 用户字段校验

        getLock(user);
        userService.insert(user, currentUser.getUserId());
        deleteLock(user);
        return Result.success();
    }

    private void getLock(User user) {

        long beginAt = System.currentTimeMillis();
        boolean usernameLock = false;
        boolean phoneLock = false;
    }

    private void deleteLock(User user) {

    }

    @DeleteMapping(value = "{userId}")
    public Result deleteUser(@RequestBody String userId, @CurrentUser User user) {

        userService.delete(userId, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateUser(@RequestBody User user, @CurrentUser User currentUser) {

        userService.update(user, currentUser.getUserId());
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
}
