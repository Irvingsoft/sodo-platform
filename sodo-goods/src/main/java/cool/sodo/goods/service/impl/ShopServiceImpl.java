package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.starter.domain.Shop;
import cool.sodo.goods.entity.ShopDTO;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.ShopMapper;
import cool.sodo.goods.service.ScheduleService;
import cool.sodo.goods.service.ShopMenuService;
import cool.sodo.goods.service.ShopService;
import cool.sodo.goods.service.ShopToCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShopServiceImpl implements ShopService {

    public static final String ERROR_SELECT = "店铺不存在！";
    public static final String ERROR_UPDATE = "店铺信息更新失败！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_PRIVACY = 2;
    public static final int SELECT_INFO = 3;

    @Resource
    private ScheduleService scheduleService;
    @Resource
    private ShopMenuService shopMenuService;
    @Resource
    private ShopToCategoryService shopToCategoryService;
    @Resource
    private ShopMapper shopMapper;

    private LambdaQueryWrapper<Shop> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = Wrappers.lambdaQuery();
        shopLambdaQueryWrapper.select(Shop::getShopId, Shop::getName
                , Shop::getAvatarUrl, Shop::getGoodsNum, Shop::getSign
                , Shop::getLeastPrice, Shop::getDeliveryPrice, Shop::getScore
                , Shop::getOrdersMonth, Shop::getOrdersWeek, Shop::getOrdersDay
                , Shop::getPredestine, Shop::getPick, Shop::getDelivery, Shop::getOpen);

        switch (type) {
            case SELECT_IDENTITY: {
                shopLambdaQueryWrapper = Wrappers.lambdaQuery();
                shopLambdaQueryWrapper.select(Shop::getShopId, Shop::getUserId, Shop::getSchoolId, Shop::getAreaId);
            }
            break;
            case SELECT_PRIVACY: {
                shopLambdaQueryWrapper.select(Shop::getUserId, Shop::getSchoolId, Shop::getAreaId
                        , Shop::getFloor, Shop::getStall, Shop::getBackgroundUrl, Shop::getPhone
                        , Shop::getNotice, Shop::getOrdersAll);
            }
            break;
            case SELECT_INFO: {
                shopLambdaQueryWrapper.select(Shop::getSchoolId, Shop::getAreaId, Shop::getFloor
                        , Shop::getStall, Shop::getBackgroundUrl, Shop::getPhone, Shop::getNotice
                        , Shop::getOrdersAll, Shop::getIncomeAll, Shop::getIncomeMonth, Shop::getIncomeWeek
                        , Shop::getIncomeDay, Shop::getAccountBalance, Shop::getValid);
            }
            break;
            case SELECT_BASE:
            default:
                break;
        }
        return shopLambdaQueryWrapper;
    }

    private void setSelectOrderBy(LambdaQueryWrapper<Shop> shopLambdaQueryWrapper, int type) {

        shopLambdaQueryWrapper.orderByDesc(Shop::getRank);
        switch (type) {
            case 1:
                shopLambdaQueryWrapper.orderByDesc(Shop::getOrdersWeek);
                break;
            case 2:
                shopLambdaQueryWrapper.orderByDesc(Shop::getOrdersMonth);
                break;
            case 3:
                shopLambdaQueryWrapper.orderByDesc(Shop::getOrdersAll);
                break;
            case 0:
            default:
                shopLambdaQueryWrapper.orderByDesc(Shop::getOrdersDay);
                break;
        }
    }

    @Override
    public void updateShop(Shop shop) {

        Shop oldShop = getShop(shop.getShopId());

        oldShop.update(shop);
        if (shopMapper.updateById(oldShop) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, oldShop.getShopId());
        }
    }

    @Override
    public Shop getShop(String shopId) {

        Shop shop = shopMapper.selectById(shopId);
        if (shop == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return shop;
    }

    @Override
    public Shop getShopByUser(String userId) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        shopLambdaQueryWrapper.eq(Shop::getUserId, userId);
        Shop shop = shopMapper.selectOne(shopLambdaQueryWrapper);
        if (shop == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return shop;
    }

    @Override
    public Shop getShopIdentity(String shopId) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        shopLambdaQueryWrapper.eq(Shop::getShopId, shopId);
        Shop shop = shopMapper.selectOne(shopLambdaQueryWrapper);
        if (shop == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return shop;
    }

    @Override
    public Shop getShopIdentityByUser(String userId) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        shopLambdaQueryWrapper.eq(Shop::getUserId, userId);
        Shop shop = shopMapper.selectOne(shopLambdaQueryWrapper);
        if (shop == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return shop;
    }

    @Override
    public Shop getShopBase(String shopId) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        shopLambdaQueryWrapper.eq(Shop::getShopId, shopId)
                .eq(Shop::getValid, true);
        Shop shop = shopMapper.selectOne(shopLambdaQueryWrapper);
        if (shop == null) {
            System.out.println("null");
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        shop.setInBusiness(shop.getOpen() && scheduleService.isInSchedule(shop.getShopId()));
        return shop;
    }

    @Override
    public Shop getShopPrivacy(String shopId) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_PRIVACY);
        shopLambdaQueryWrapper.eq(Shop::getShopId, shopId)
                .eq(Shop::getValid, true);
        Shop shop = shopMapper.selectOne(shopLambdaQueryWrapper);
        if (shop == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        shop.setInBusiness(shop.getOpen() && scheduleService.isInSchedule(shop.getShopId()));
        return shop;
    }

    @Override
    public IPage<Shop> pageShopBaseBySchool(ShopDTO shopDTO) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        shopLambdaQueryWrapper.eq(Shop::getSchoolId, shopDTO.getSchoolId())
                .eq(Shop::getOpen, true)
                .eq(Shop::getValid, true);
        setSelectOrderBy(shopLambdaQueryWrapper, shopDTO.getType());

        Page<Shop> shopPage = new Page<>(shopDTO.getPageNum(), shopDTO.getPageSize());
        IPage<Shop> shopInPage = shopMapper.selectPage(shopPage, shopLambdaQueryWrapper);
        shopInPage.getRecords().forEach(
                shop -> shop.setInBusiness(scheduleService.isInSchedule(shop.getShopId()))
        );
        return shopInPage;
    }

    @Override
    public IPage<Shop> pageShopBaseByCategory(ShopDTO shopDTO) {

        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        List<String> shopIdList = shopToCategoryService.listShopIdByCategory(shopDTO.getCategoryId());
        if (shopIdList.size() != 0) {
            shopLambdaQueryWrapper.in(Shop::getShopId, shopIdList);
            shopLambdaQueryWrapper.eq(Shop::getOpen, true)
                    .eq(Shop::getValid, true);
            setSelectOrderBy(shopLambdaQueryWrapper, shopDTO.getType());

            Page<Shop> shopPage = new Page<>(shopDTO.getPageNum(), shopDTO.getPageSize());
            IPage<Shop> shopInPage = shopMapper.selectPage(shopPage, shopLambdaQueryWrapper);
            shopInPage.getRecords().forEach(
                    shop -> shop.setInBusiness(scheduleService.isInSchedule(shop.getShopId()))
            );
            return shopInPage;
        } else {
            return null;
        }
    }

    @Override
    public Shop getShopDetail(String shopId) {

        Shop shop = getShopPrivacy(shopId);
        shop.setInBusiness(shop.getOpen() && scheduleService.isInSchedule(shop.getShopId()));
        shop.setScheduleList(scheduleService.listScheduleBaseByObject(shopId));
        shop.setShopMenuList(shopMenuService.listShopMenuBaseDetail(shopId));
        return shop;
    }
}
