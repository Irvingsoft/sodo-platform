package cool.sodo.goods.service;

import cool.sodo.common.domain.GoodsSet;

import java.util.List;

public interface GoodsSetService {

    void insertGoodsSet(GoodsSet goodsSet, String shopId);

    void deleteGoodsSet(GoodsSet goodsSet, String shopId);

    void deleteSetByGoods(String goodsId);

    void updateGoodsSet(GoodsSet goodsSet, String shopId);

    GoodsSet getGoodsSet(String setId);

    List<GoodsSet> listGoodsSetBaseByGoods(String goodsId);

    List<GoodsSet> listGoodsSetBaseDetailByGoods(String goodsId);
}
