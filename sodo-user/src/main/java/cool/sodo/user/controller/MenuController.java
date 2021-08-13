package cool.sodo.user.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Result;
import cool.sodo.user.service.MenuService;
import cool.sodo.user.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "menu")
public class MenuController {

    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

    @GetMapping(value = "route/{clientId}")
    public Result route(@PathVariable String clientId, @CurrentUser User user) {

        List<String> roleIdList = roleService.listRoleRoleId(user.getUserId(), clientId);
        return Result.success(menuService.route(roleIdList, clientId));
    }

    @GetMapping(value = "button/{clientId}")
    public Result button(@PathVariable String clientId, @CurrentUser User user) {

        List<String> roleIdList = roleService.listRoleRoleId(user.getUserId(), clientId);
        return Result.success(menuService.button(roleIdList, clientId));
    }
}
