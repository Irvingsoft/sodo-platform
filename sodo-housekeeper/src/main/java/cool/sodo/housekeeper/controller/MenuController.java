package cool.sodo.housekeeper.controller;

import cool.sodo.common.base.entity.Result;
import cool.sodo.common.core.domain.Menu;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.starter.annotation.CurrentUser;
import cool.sodo.housekeeper.entity.MenuDTO;
import cool.sodo.housekeeper.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping(value = "tree/{clientId}")
    public Result treeMenu(@PathVariable String clientId) {
        return Result.success(menuService.treeMenu(clientId));
    }

    @PostMapping(value = "list")
    public Result listMenu(@RequestBody @Valid MenuDTO menuDTO) {
        return Result.success(menuService.listMenu(menuDTO));
    }

    @GetMapping(value = "list/{roleId}")
    public Result listMenu(@PathVariable String roleId) {
        return Result.success(menuService.listMenu(roleId));
    }

    @GetMapping(value = "{menuId}")
    public Result getMenu(@PathVariable String menuId) {
        return Result.success(menuService.getMenu(menuId));
    }

    @PostMapping(value = "")
    public Result insertMenu(@RequestBody Menu menu, @CurrentUser User user) {

        menuService.insertMenu(menu, user.getUserId());
        return Result.success();
    }

    @PatchMapping(value = "")
    public Result updateMenu(@RequestBody Menu menu, @CurrentUser User user) {

        menuService.updateMenu(menu, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "{menuId}")
    public Result deleteMenu(@PathVariable String menuId, @CurrentUser User user) {

        menuService.deleteMenu(menuId, user.getUserId());
        return Result.success();
    }

    @DeleteMapping(value = "list")
    public Result deleteMenuList(@RequestBody List<String> menuIdList, @CurrentUser User user) {

        menuService.deleteMenu(menuIdList, user.getUserId());
        return Result.success();
    }
}
