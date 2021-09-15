package cool.sodo.goods.controller;

import cool.sodo.common.base.entity.Result;
import cool.sodo.common.starter.domain.GoodsChoice;
import cool.sodo.common.starter.domain.Shop;
import cool.sodo.goods.annotation.CurrentShop;
import cool.sodo.goods.service.GoodsChoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "goodsChoice")
@Api(tags = "商品选项相关接口")
public class GoodsChoiceController {

    @Resource
    private GoodsChoiceService goodsChoiceService;

    @PostMapping(value = {"", "/"})
    @ApiOperation(value = "")
    public Result insertGoodsChoice(@RequestBody GoodsChoice goodsChoice, @CurrentShop Shop shop) {

        goodsChoiceService.insertGoodsChoice(goodsChoice, shop.getShopId());
        return Result.success();
    }

    @DeleteMapping(value = "/{choiceId}")
    @ApiOperation(value = "")
    public Result deleteGoodsChoice(@PathVariable String choiceId, @CurrentShop Shop shop) {

        goodsChoiceService.deleteGoodsChoice(goodsChoiceService.getGoodsChoice(choiceId), shop.getShopId());
        return Result.success();
    }

    @PatchMapping(value = {"", "/"})
    @ApiOperation(value = "")
    public Result updateGoodsChoice(@RequestBody @Valid GoodsChoice goodsChoice, @CurrentShop Shop shop) {

        GoodsChoice oldGoodsChoice = goodsChoiceService.getGoodsChoice(goodsChoice.getChoiceId());
        goodsChoice.setShopId(oldGoodsChoice.getShopId());
        goodsChoice.setSetId(oldGoodsChoice.getSetId());
        goodsChoiceService.updateGoodsChoice(goodsChoice, shop.getShopId());
        return Result.success();
    }

    @GetMapping(value = "/{choiceId}")
    @ApiOperation(value = "")
    public Result getInfo(@PathVariable String choiceId) {

        return Result.success(goodsChoiceService.getGoodsChoice(choiceId));
    }

    @GetMapping(value = "listBase/{setId}")
    @ApiOperation(value = "")
    public Result listChoiceBaseBySet(@PathVariable String setId) {

        return Result.success(goodsChoiceService.listGoodsChoiceBaseByGoodsSet(setId));
    }
}
