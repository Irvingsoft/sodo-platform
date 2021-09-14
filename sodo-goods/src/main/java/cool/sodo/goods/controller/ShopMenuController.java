package cool.sodo.goods.controller;

import cool.sodo.common.starter.domain.Shop;
import cool.sodo.common.starter.domain.ShopMenu;
import cool.sodo.common.base.entity.Result;
import cool.sodo.goods.annotation.CurrentShop;
import cool.sodo.goods.service.ShopMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "shopMenu")
@Api(tags = "店铺分类相关接口")
public class ShopMenuController {

    @Resource
    private ShopMenuService shopMenuService;

    @PostMapping(value = {"", "/"})
    @ApiOperation(value = "保存店铺菜单")
    public Result insertShopMenu(@RequestBody ShopMenu shopMenu, @CurrentShop Shop shop) {

        shopMenuService.insertShopMenu(shopMenu, shop.getShopId());
        return Result.success();
    }

    @DeleteMapping(value = "/{menuId}")
    @ApiOperation(value = "删除店铺菜单")
    public Result deleteShopMenu(@PathVariable String menuId, @CurrentShop Shop shop) {

        shopMenuService.deleteShopMenu(shopMenuService.getShopMenu(menuId), shop.getShopId());
        return Result.success();
    }

    @PatchMapping(value = {"", "/"})
    @ApiOperation(value = "保存店铺菜单")
    public Result updateShopMenu(@RequestBody @Valid ShopMenu shopMenu, @CurrentShop Shop shop) {

        shopMenuService.updateShopMenu(shopMenu, shop.getShopId());
        return Result.success();
    }

    @GetMapping(value = "list/base")
    @ApiOperation(value = "")
    public Result listMenuBase(@CurrentShop Shop shop) {

        return Result.success(shopMenuService.listShopMenuBase(shop.getShopId()));
    }

    /**
     * 店主更新后菜单列表后重新查询菜单列表的接口，其中包括菜单下对应的商品简介
     *
     * @author TimeChaser
     * @date 2020/11/25 11:09 上午
     */
    @GetMapping(value = "list/detail")
    @ApiOperation(value = "店主查询菜单列表")
    public Result listMenuDetail(@CurrentShop Shop shop) {

        return Result.success(shopMenuService.listShopMenuBaseDetail(shop.getShopId()));
    }
}
