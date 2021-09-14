package cool.sodo.goods.component;

import cool.sodo.common.starter.domain.Shop;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.ShopMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@Component
public class ShopAsyncTask {

    public static final String ERROR_UPDATE = "店铺信息更新失败";
    public static final String ERROR_GOODS_NUM = "店铺商品计数异常";

    @Resource
    private ShopMapper shopMapper;

    @Async
    public void increaseGoodsNum(@NotNull(message = "店铺 ID 不能为空") String shopId) {
        Shop shop = shopMapper.selectById(shopId);
        shop.setGoodsNum(shop.getGoodsNum() + 1);
        if (shopMapper.updateById(shop) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, shopId);
        }
    }

    @Async
    public void decreaseGoodsNum(@NotNull(message = "店铺 ID 不能为空") String shopId) {
        Shop shop = shopMapper.selectById(shopId);
        shop.setGoodsNum(shop.getGoodsNum() - 1);
        if (shop.getGoodsNum() < 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_GOODS_NUM, shopId);
        }
        if (shopMapper.updateById(shop) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, shopId);
        }
    }
}
