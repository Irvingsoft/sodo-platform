package cool.sodo.goods.component;

import cool.sodo.common.base.domain.ShopMenu;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.ShopMenuMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 异步操作店铺菜单的信息变更
 *
 * @author TimeChaser
 * @date 2021/5/27 10:40
 */
@Component
public class ShopMenuAsyncTask {

    public static final String ERROR_UPDATE = "商品菜单更新失败";
    public static final String ERROR_GOODS_NUM = "商品菜单商品计数异常";

    @Resource
    private ShopMenuMapper shopMenuMapper;
    @Resource
    private ShopAsyncTask shopAsyncTask;

    @Async
    public void increaseGoodsNum(@NotNull(message = "商品所属菜单 ID 不能为空") String menuId) {

        ShopMenu shopMenu = shopMenuMapper.selectById(menuId);
        shopMenu.setGoodsNum(shopMenu.getGoodsNum() + 1);
        if (shopMenuMapper.updateById(shopMenu) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, menuId);
        }
        shopAsyncTask.increaseGoodsNum(shopMenu.getShopId());
    }

    @Async
    public void decreaseGoodsNum(@NotNull(message = "商品所属菜单 ID 不能为空") String menuId) {

        ShopMenu shopMenu = shopMenuMapper.selectById(menuId);
        shopMenu.setGoodsNum(shopMenu.getGoodsNum() - 1);
        if (shopMenu.getGoodsNum() < 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_GOODS_NUM, menuId);
        }
        if (shopMenuMapper.updateById(shopMenu) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, menuId);
        }
        shopAsyncTask.decreaseGoodsNum(shopMenu.getShopId());
    }
}
