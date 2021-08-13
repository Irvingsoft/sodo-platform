package cool.sodo.goods.service;

import cool.sodo.common.domain.ShopMenu;

import java.util.List;

public interface ShopMenuService {

    void insertShopMenu(ShopMenu shopMenu, String shopId);

    void deleteShopMenu(ShopMenu shopMenu, String shopId);

    void updateShopMenu(ShopMenu shopMenu, String shopId);

    ShopMenu getShopMenu(String menuId);

    ShopMenu getShopMenuIdentity(String menuId);

    ShopMenu getShopMenuBase(String menuId);

    List<ShopMenu> listShopMenuBase(String shopId);

    List<ShopMenu> listShopMenuBaseDetail(String shopId);

}
