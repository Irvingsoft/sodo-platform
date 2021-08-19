package cool.sodo.housekeeper.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.domain.Role;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Result;
import cool.sodo.housekeeper.entity.RoleRequest;
import cool.sodo.housekeeper.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping(value = "tree/{clientId}")
    public Result tree(@PathVariable String clientId) {
        return Result.success(roleService.tree(clientId));
    }

    @PostMapping(value = "list")
    public Result listRole(@RequestBody @Valid RoleRequest roleRequest) {
        return Result.success(roleService.listRole(roleRequest));
    }

    @GetMapping(value = "{roleId}")
    public Result getRole(@PathVariable String roleId) {
        return Result.success(roleService.getRole(roleId));
    }

    @PostMapping(value = "")
    public Result insertRole(@RequestBody Role role, @CurrentUser User user) {

        roleService.insertRole(role, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "{roleId}")
    public Result deleteRole(@PathVariable String roleId, @CurrentUser User user) {

        roleService.deleteRole(roleId, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateRole(@RequestBody Role role, @CurrentUser User user) {

        roleService.updateRole(role, user.getUserId());
        return Result.success();
    }
}
