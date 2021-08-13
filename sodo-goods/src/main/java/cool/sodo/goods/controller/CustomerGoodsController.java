package cool.sodo.goods.controller;

import cool.sodo.common.entity.Result;
import cool.sodo.goods.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "customer/goods")
@Api(tags = "商品顾客接口")
public class CustomerGoodsController {

    @Resource
    private GoodsService goodsService;


    @GetMapping(value = "/{goodsId}")
    @ApiOperation(value = "获取商品信息")
    public Result getGoodsBase(@PathVariable String goodsId) {

        return Result.success(goodsService.getGoodsBase(goodsId));
    }

    @GetMapping(value = "detail/{goodsId}")
    @ApiOperation(value = "获取商品详情信息")
    public Result getGoodsDetail(@PathVariable String goodsId) {

        return Result.success(goodsService.getGoodsDetail(goodsId));
    }

    @GetMapping(value = "list/{menuId}")
    @ApiOperation(value = "")
    public Result listGoodsBase(@PathVariable String menuId) {

        return Result.success(goodsService.listGoodsBaseByShopMenu(menuId));
    }

}
