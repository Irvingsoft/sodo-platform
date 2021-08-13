package cool.sodo.goods.controller;

import cool.sodo.common.domain.GoodsSet;
import cool.sodo.common.domain.Shop;
import cool.sodo.common.entity.Result;
import cool.sodo.goods.annotation.CurrentShop;
import cool.sodo.goods.service.GoodsSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "goodsSet")
@Api(tags = "商品选项组相关接口")
public class GoodsSetController {

    @Resource
    private GoodsSetService goodsSetService;

    @PostMapping(value = {"", "/"})
    @ApiOperation(value = "")
    public Result insertGoodsSet(@RequestBody @Valid GoodsSet goodsSet, @CurrentShop Shop shop) {

        goodsSetService.insertGoodsSet(goodsSet, shop.getShopId());
        return Result.success();
    }

    @DeleteMapping(value = "/{setId}")
    @ApiOperation(value = "")
    public Result deleteGoodsSet(@PathVariable String setId, @CurrentShop Shop shop) {

        goodsSetService.deleteGoodsSet(goodsSetService.getGoodsSet(setId), shop.getShopId());
        return Result.success();
    }

    @PatchMapping(value = {"", "/"})
    @ApiOperation(value = "")
    public Result updateGoodsSet(@RequestBody @Valid GoodsSet goodsSet, @CurrentShop Shop shop) {

        goodsSetService.updateGoodsSet(goodsSet, shop.getShopId());
        return Result.success();
    }

    @GetMapping(value = "/{setId}")
    @ApiOperation(value = "")
    public Result getInfo(@PathVariable String setId) {

        return Result.success(goodsSetService.getGoodsSet(setId));
    }

    @GetMapping(value = "listBase/{goodId}")
    @ApiOperation(value = "")
    public Result listSetBase(@PathVariable String goodsId) {

        return Result.success(goodsSetService.listGoodsSetBaseByGoods(goodsId));
    }

    @GetMapping(value = "listDetail/{goodsId}")
    @ApiOperation(value = "")
    public Result listSetDetail(@PathVariable String goodsId) {

        return Result.success(goodsSetService.listGoodsSetBaseDetailByGoods(goodsId));
    }
}
