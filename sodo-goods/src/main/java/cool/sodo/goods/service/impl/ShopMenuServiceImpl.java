package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.ShopMenu;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.ShopMenuMapper;
import cool.sodo.goods.service.GoodsService;
import cool.sodo.goods.service.ShopMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShopMenuServiceImpl implements ShopMenuService {

    public static final String ERROR_SELECT = "商品菜单不存在！";
    public static final String ERROR_INSERT = "商品菜单新增失败";
    public static final String ERROR_DELETE = "商品菜单删除失败";
    public static final String ERROR_UPDATE = "商品菜单更新失败";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;

    @Resource
    private ShopMenuMapper shopMenuMapper;
    @Resource
    private GoodsService goodsService;

    private LambdaQueryWrapper<ShopMenu> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<ShopMenu> shopMenuLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                shopMenuLambdaQueryWrapper.select(ShopMenu::getMenuId, ShopMenu::getShopId);
                break;
            case SELECT_BASE:
                shopMenuLambdaQueryWrapper.select(ShopMenu::getMenuId, ShopMenu::getName, ShopMenu::getGoodsNum, ShopMenu::getSort);
                break;
            default:
                break;
        }
        return shopMenuLambdaQueryWrapper;
    }

    @Override
    public void insertShopMenu(ShopMenu shopMenu, String shopId) {

        shopMenu.setShopId(shopId);
        if (shopMenuMapper.insert(shopMenu) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_INSERT, shopId);
        }
    }

    @Override
    public void deleteShopMenu(ShopMenu shopMenu, String shopId) {

        if (shopMenuMapper.deleteById(shopMenu.getMenuId()) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_DELETE, shopMenu.getMenuId());
        }
    }

    @Override
    public void updateShopMenu(ShopMenu shopMenu, String shopId) {

        ShopMenu oldMenu = getShopMenu(shopMenu.getMenuId());

        oldMenu.update(shopMenu);
        if (shopMenuMapper.updateById(oldMenu) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, oldMenu.getMenuId());
        }
    }

    @Override
    public ShopMenu getShopMenu(String menuId) {

        ShopMenu shopMenu = shopMenuMapper.selectById(menuId);
        if (shopMenu == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, menuId);
        }
        return shopMenu;
    }

    @Override
    public ShopMenu getShopMenuIdentity(String menuId) {

        LambdaQueryWrapper<ShopMenu> shopMenuLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        shopMenuLambdaQueryWrapper.eq(ShopMenu::getMenuId, menuId);
        ShopMenu shopMenu = shopMenuMapper.selectOne(shopMenuLambdaQueryWrapper);
        if (shopMenu == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, menuId);
        }
        return shopMenu;
    }

    @Override
    public ShopMenu getShopMenuBase(String menuId) {

        LambdaQueryWrapper<ShopMenu> shopMenuLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        shopMenuLambdaQueryWrapper.eq(ShopMenu::getMenuId, menuId);
        ShopMenu shopMenu = shopMenuMapper.selectOne(shopMenuLambdaQueryWrapper);
        if (shopMenu == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT, menuId);
        }
        return shopMenu;
    }

    @Override
    public List<ShopMenu> listShopMenuBase(String shopId) {

        LambdaQueryWrapper<ShopMenu> shopMenuLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        shopMenuLambdaQueryWrapper.eq(ShopMenu::getShopId, shopId)
                .orderByAsc(ShopMenu::getSort);

        return shopMenuMapper.selectList(shopMenuLambdaQueryWrapper);
    }

    @Override
    public List<ShopMenu> listShopMenuBaseDetail(String shopId) {

        List<ShopMenu> shopMenuList = listShopMenuBase(shopId);

        if (shopMenuList != null) {
            shopMenuList.forEach(
                    shopMenu -> shopMenu.setGoodsList(goodsService.listGoodsBaseDetailByShopMenu(shopMenu.getMenuId()))
            );
        }
        return shopMenuList;
    }

}
