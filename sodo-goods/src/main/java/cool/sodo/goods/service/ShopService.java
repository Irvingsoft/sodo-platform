package cool.sodo.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.Shop;
import cool.sodo.goods.entity.ShopDTO;

public interface ShopService {

    void updateShop(Shop shop);

    Shop getShop(String shopId);

    Shop getShopByUser(String userId);

    Shop getShopIdentity(String shopId);

    Shop getShopIdentityByUser(String userId);

    Shop getShopBase(String shopId);

    Shop getShopPrivacy(String shopId);

    IPage<Shop> pageShopBaseBySchool(ShopDTO shopDTO);

    IPage<Shop> pageShopBaseByCategory(ShopDTO shopDTO);

    Shop getShopDetail(String shopId);
}
