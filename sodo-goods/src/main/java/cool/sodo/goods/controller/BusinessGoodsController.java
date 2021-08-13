package cool.sodo.goods.controller;

import cool.sodo.common.domain.Goods;
import cool.sodo.common.domain.Shop;
import cool.sodo.common.entity.Result;
import cool.sodo.goods.annotation.CurrentShop;
import cool.sodo.goods.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "business/goods")
@Api(tags = "商品商家接口")
public class BusinessGoodsController {

    @Resource
    private GoodsService goodsService;

    @PostMapping(value = {"", "/"})
    @ApiOperation(value = "增加商品")
    public Result insertGoods(@RequestBody @Valid Goods goods, @CurrentShop Shop shop) {

        goodsService.insertGoods(goods, shop.getShopId());
        return Result.success();
    }

    @DeleteMapping(value = "/{goodsId}")
    @ApiOperation(value = "删除商品")
    public Result deleteGoods(@PathVariable String goodsId, @CurrentShop Shop shop) {

        goodsService.deleteGoods(goodsService.getGoodsBase(goodsId), shop.getShopId());
        return Result.success();
    }

    @PatchMapping(value = {"", "/"})
    @ApiOperation(value = "更新商品")
    public Result updateGoods(@RequestBody @Valid Goods goods, @CurrentShop Shop shop) {

        goodsService.updateGoods(goods, shop.getShopId());
        return Result.success();
    }

    @GetMapping(value = "/{goodsId}")
    @ApiOperation(value = "获取商品信息")
    public Result getGoodsInfo(@PathVariable String goodsId, @CurrentShop Shop shop) {

        return Result.success(goodsService.getGoodsInfo(goodsId, shop.getShopId()));
    }
}
