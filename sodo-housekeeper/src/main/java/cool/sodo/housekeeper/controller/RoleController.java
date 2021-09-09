package cool.sodo.housekeeper.controller;

import cool.sodo.common.base.domain.Role;
import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.core.annotation.CurrentUser;
import cool.sodo.housekeeper.entity.GrantDTO;
import cool.sodo.housekeeper.entity.RoleDTO;
import cool.sodo.housekeeper.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping(value = "tree/{clientId}")
    public Result treeRole(@PathVariable String clientId) {
        return Result.success(roleService.treeRoleByClient(clientId));
    }

    @PostMapping(value = "list")
    public Result listRole(@RequestBody @Valid RoleDTO roleDTO) {
        return Result.success(roleService.listRole(roleDTO));
    }

    @GetMapping(value = "list/{userId}")
    public Result listRole(@PathVariable String userId) {
        return Result.success(roleService.listRole(userId));
    }

    @PostMapping(value = "grant")
    public Result grant(@RequestBody GrantDTO grantDTO) {

        roleService.grant(grantDTO.getRoleIdList(), grantDTO.getMenuIdList());
        return Result.success();
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

    @DeleteMapping(value = "list")
    public Result deleteRoleList(@RequestBody List<String> roleIdList, @CurrentUser User user) {

        roleService.deleteRole(roleIdList, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateRole(@RequestBody Role role, @CurrentUser User user) {

        roleService.updateRole(role, user.getUserId());
        return Result.success();
    }
}
