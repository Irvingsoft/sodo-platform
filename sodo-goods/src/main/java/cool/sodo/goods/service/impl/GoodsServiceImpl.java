package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.starter.domain.Goods;
import cool.sodo.goods.component.ShopMenuAsyncTask;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.GoodsMapper;
import cool.sodo.goods.service.GoodsService;
import cool.sodo.goods.service.GoodsSetService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements GoodsService {


    public static final String ERROR_SELECT = "商品不存在！";
    public static final String ERROR_INSERT = "商品新增失败！";
    public static final String ERROR_DELETE = "商品删除失败！";
    public static final String ERROR_UPDATE = "商品更新失败！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsSetService goodsSetService;
    @Resource
    private ShopMenuAsyncTask shopMenuAsyncTask;

    private LambdaQueryWrapper<Goods> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                goodsLambdaQueryWrapper.select(Goods::getGoodsId, Goods::getShopId, Goods::getMenuId);
                break;
            case SELECT_BASE:
                goodsLambdaQueryWrapper.select(Goods::getGoodsId, Goods::getName, Goods::getAvatarUrl, Goods::getWeight
                        , Goods::getDescription, Goods::getPriceDescription, Goods::getOriginalPrice, Goods::getDiscountPrice
                        , Goods::getPackingPrice, Goods::getDiscountLimit, Goods::getDiscount, Goods::getScore
                        , Goods::getOrdersMonth, Goods::getOrdersWeek, Goods::getOrdersDay, Goods::getStockNum
                        , Goods::getStock);

                break;
            case SELECT_INFO:
                goodsLambdaQueryWrapper.select(Goods::getGoodsId, Goods::getShopId, Goods::getMenuId, Goods::getName, Goods::getAvatarUrl, Goods::getWeight
                        , Goods::getDescription, Goods::getPriceDescription, Goods::getOriginalPrice, Goods::getDiscountPrice
                        , Goods::getPackingPrice, Goods::getDiscountLimit, Goods::getDiscount, Goods::getScore
                        , Goods::getOrdersAll, Goods::getOrdersMonth, Goods::getOrdersWeek, Goods::getOrdersDay
                        , Goods::getIncomeAll, Goods::getIncomeMonth, Goods::getIncomeWeek, Goods::getIncomeDay
                        , Goods::getStockNum, Goods::getStock, Goods::getSort, Goods::getSell);
            default:
                break;
        }
        return goodsLambdaQueryWrapper;
    }

    @Override
    public void insertGoods(Goods goods, String shopId) {

        // TODO Insert 初始化优化·ID/ShopId
        goods.setShopId(shopId);
        if (goodsMapper.insert(goods) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_INSERT, shopId);
        }
        shopMenuAsyncTask.increaseGoodsNum(goods.getMenuId());
    }

    @Override
    @CacheEvict(cacheNames = "goods", key = "#root.args[0]")
    public void deleteGoods(Goods goods, String shopId) {

        if (goodsMapper.deleteById(goods.getGoodsId()) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_DELETE, goods.getGoodsId());
        }
        shopMenuAsyncTask.decreaseGoodsNum(goods.getMenuId());
        goodsSetService.deleteSetByGoods(goods.getGoodsId());
    }

    @Override
    @CacheEvict(cacheNames = "goods", key = "#root.args[0].goodsId")
    public void updateGoods(Goods goods, String shopId) {

        Goods oldGoods = getGoods(goods.getGoodsId());

        if (!goods.getMenuId().equals(oldGoods.getMenuId())) {
            shopMenuAsyncTask.decreaseGoodsNum(oldGoods.getMenuId());
            shopMenuAsyncTask.increaseGoodsNum(goods.getMenuId());
        }
        oldGoods.update(goods);
        if (goodsMapper.updateById(oldGoods) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, goods.getGoodsId());
        }
    }

    @Override
    public Goods getGoods(String goodsId) {

        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, goodsId);
        }
        return goods;
    }

    @Override
    public Goods getGoodsIdentity(String goodsId) {

        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        goodsLambdaQueryWrapper.eq(Goods::getGoodsId, goodsId);
        Goods goods = goodsMapper.selectOne(goodsLambdaQueryWrapper);
        if (goods == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, goodsId);
        }
        return goods;
    }

    @Override
    public Goods getGoodsBase(String goodsId) {

        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        goodsLambdaQueryWrapper.eq(Goods::getGoodsId, goodsId);
        Goods goods = goodsMapper.selectOne(goodsLambdaQueryWrapper);
        if (goods == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, goodsId);
        }
        return goods;
    }

    @Override
    public Goods getGoodsInfo(String goodsId, String shopId) {

        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        goodsLambdaQueryWrapper.eq(Goods::getGoodsId, goodsId);
        Goods goods = goodsMapper.selectOne(goodsLambdaQueryWrapper);
        if (goods == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, goodsId);
        }
        if (!goods.getShopId().equals(shopId)) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, Constants.ERROR_LIMITS_AUTHORITY, shopId);
        }
        return goods;
    }

    @Override
    @Cacheable(cacheNames = "goods", key = "#root.args[0]")
    public Goods getGoodsDetail(String goodsId) {

        Goods goods = getGoodsBase(goodsId);
        goods.handlePrivacy();
        goods.setGoodsSetList(goodsSetService.listGoodsSetBaseDetailByGoods(goodsId));
        return goods;
    }

    @Override
    public List<Goods> listGoodsBaseByShopMenu(String menuId) {

        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        goodsLambdaQueryWrapper.eq(Goods::getMenuId, menuId)
                .eq(Goods::getSell, true)
                .and(wrapper -> wrapper
                        .eq(Goods::getStock, false)
                        .or()
                        .gt(Goods::getStockNum, 0)
                );
        return goodsMapper.selectList(goodsLambdaQueryWrapper);
    }

    @Override
    public List<Goods> listGoodsBaseDetailByShopMenu(String menuId) {

        List<Goods> goodsList = listGoodsBaseByShopMenu(menuId);
        if (goodsList != null) {
            goodsList.forEach(
                    goods -> {
                        goods.handlePrivacy();
                        goods.setGoodsSetList(goodsSetService.listGoodsSetBaseDetailByGoods(goods.getGoodsId()));
                    }
            );
        }
        return null;
    }
}

// TODO:
//  1. 商品类 Goods.class 补充字段 => ok，优化用户店铺查询 => ok
//  2. 异常处理优化
//  3. 接口抽离
//  4. 规格组隶属于店铺而不是商品，规格组价格 => ok
//  5. @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) => ok，只能被写入，不能被以 Json 格式序列化到客户端
//  6. API 管理
//  7. 用户与客户端的匹配 => ok