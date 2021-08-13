package cool.sodo.goods.service;

import cool.sodo.common.domain.ShopToCategory;

import java.util.List;

public interface ShopToCategoryService {

    List<String> listShopIdByCategory(String categoryId);

    List<ShopToCategory> listShopToCategoryByCategory(String categoryId, Integer pageNum, Integer pageSize);
}
