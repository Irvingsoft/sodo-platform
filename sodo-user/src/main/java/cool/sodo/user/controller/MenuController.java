package cool.sodo.user.controller;

import cool.sodo.common.base.entity.Result;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.core.service.CommonRoleService;
import cool.sodo.common.starter.annotation.CurrentUser;
import cool.sodo.user.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
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
    private CommonRoleService roleService;

    @GetMapping(value = "route")
    public Result route(@CurrentUser User user) {

        List<String> roleIdList = roleService.listRoleId(user.getUserId());
        return Result.success(menuService.route(roleIdList));
    }

    @GetMapping(value = "button")
    public Result button(@CurrentUser User user) {

        List<String> roleIdList = roleService.listRoleId(user.getUserId());
        return Result.success(menuService.button(roleIdList));
    }
}
