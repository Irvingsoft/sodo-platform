package cool.sodo.goods.service;

import cool.sodo.common.domain.GoodsChoice;

import java.util.List;

public interface GoodsChoiceService {

    void insertGoodsChoice(GoodsChoice goodsChoice, String shopId);

    void deleteGoodsChoice(GoodsChoice goodsChoice, String shopId);

    void deleteGoodsChoice(String choiceId);

    void deleteChoiceByGoodsSet(String setId);

    void updateGoodsChoice(GoodsChoice goodsChoice, String shopId);

    GoodsChoice getGoodsChoice(String choiceId);

    List<GoodsChoice> listGoodsChoiceBaseByGoodsSet(String setId);
}
