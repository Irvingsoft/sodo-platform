package cool.sodo.goods.service;

import cool.sodo.common.domain.Goods;

import java.util.List;

public interface GoodsService {

    void insertGoods(Goods goods, String shopId);

    void deleteGoods(Goods goods, String shopId);

    void updateGoods(Goods goods, String shopId);

    Goods getGoods(String goodsId);

    Goods getGoodsIdentity(String goodsId);

    Goods getGoodsBase(String goodsId);

    Goods getGoodsInfo(String goodsId, String shopId);

    Goods getGoodsDetail(String goodsId);

    List<Goods> listGoodsBaseByShopMenu(String menuId);

    List<Goods> listGoodsBaseDetailByShopMenu(String menuId);
}
