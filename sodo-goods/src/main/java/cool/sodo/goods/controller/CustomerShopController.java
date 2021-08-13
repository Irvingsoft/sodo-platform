package cool.sodo.goods.controller;

import cool.sodo.common.entity.Result;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.goods.entity.ShopRequest;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 店铺顾客接口
 *
 * @author TimeChaser
 * @date 2021/5/26 22:57
 */
@RestController
@RequestMapping(value = "customer/shop")
@Api(tags = "店铺顾客接口")
public class CustomerShopController {

    public static final String ERROR_PARAMS = "参数有误";

    @Resource
    private ShopService shopService;

    /**
     * 根据 店铺ID 查询店铺基本信息
     *
     * @author TimeChaser
     * @date 2020/11/24 2:23 下午
     */
    @GetMapping(value = "base/{shopId}")
    @ApiOperation(value = "查询店铺基础信息")
    public Result getShopBase(@PathVariable String shopId) {

        return Result.success(shopService.getShopBase(shopId));
    }

    /**
     * 根据 店铺ID 查询店铺基本信息，其中要对敏感信息进行处理
     *
     * @author TimeChaser
     * @date 2020/11/24 2:23 下午
     */
    @GetMapping(value = "info/{shopId}")
    @ApiOperation(value = "查询店铺信息")
    public Result getShopPrivacy(@PathVariable String shopId) {

        return Result.success(shopService.getShopPrivacy(shopId));
    }

    /**
     * 小程序进入首页，根据校园 ID 查询校园内所有店铺、按热度值排序
     * <p>
     * 小程序首页头部导航栏，根据分区查询店铺
     *
     * @author TimeChaser
     * @date 2020/11/21 11:44 下午
     */
    @PostMapping(value = "list")
    @ApiOperation(value = "条件查询店铺列表")
    public Result listShopBase(@RequestBody ShopRequest shopRequest) {

        if (!StringUtils.isEmpty(shopRequest.getSchoolId())
                && StringUtils.isEmpty(shopRequest.getCategoryId())) {
            return Result.success(shopService.pageShopBaseBySchool(shopRequest));
        } else if (StringUtils.isEmpty(shopRequest.getSchoolId())
                && !StringUtils.isEmpty(shopRequest.getCategoryId())) {
            return Result.success(shopService.pageShopBaseByCategory(shopRequest));
        } else {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_PARAMS);
        }
    }

    /**
     * 根据店铺 ID 查询店铺详情、店铺分类列表、商品
     *
     * @author TimeChaser
     * @date 2020/11/24 2:18 下午
     */
    @GetMapping(value = "detail/{shopId}")
    @ApiOperation(value = "查询店铺详情信息")
    public Result getDetail(@PathVariable String shopId) {

        return Result.success(shopService.getShopDetail(shopId));
    }
}
