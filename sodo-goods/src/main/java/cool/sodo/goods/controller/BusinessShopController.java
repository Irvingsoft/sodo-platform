package cool.sodo.goods.controller;

import cool.sodo.common.annotation.CurrentUser;
import cool.sodo.common.domain.Shop;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Result;
import cool.sodo.goods.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 店铺商家接口
 *
 * @author TimeChaser
 * @date 2021/5/26 22:57
 */
@RestController
@RequestMapping(value = "business/shop")
@Api(tags = "店铺商家接口")
public class BusinessShopController {

    @Resource
    private ShopService shopService;

    /**
     * 修改店铺信息
     *
     * @author TimeChaser
     * @date 2020/11/24 2:19 下午
     */
    @PatchMapping(value = {"", "/"})
    @ApiOperation(value = "修改店铺信息")
    public Result updateShop(@RequestBody @Valid Shop shop, @CurrentUser User user) {

        if (!shopService.getShopIdentity(shop.getShopId()).getUserId().equals(user.getUserId())) {
            return Result.badRequest();
        }
        shopService.updateShop(shop);
        return Result.success();
    }

    /**
     * 店主查询旗下店铺信息
     *
     * @author TimeChaser
     * @date 2020/11/24 2:37 下午
     */
    @GetMapping(value = "user/")
    @ApiOperation(value = "查询旗下店铺信息")
    public Result getShopByUser(@CurrentUser User user) {

        return Result.success(shopService.getShopByUser(user.getUserId()));
    }

}
