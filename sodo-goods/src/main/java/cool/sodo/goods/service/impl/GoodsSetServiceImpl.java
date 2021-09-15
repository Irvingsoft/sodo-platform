package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.starter.domain.GoodsSet;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.GoodsSetMapper;
import cool.sodo.goods.service.GoodsChoiceService;
import cool.sodo.goods.service.GoodsSetService;
import cool.sodo.goods.service.GoodsToSetService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsSetServiceImpl implements GoodsSetService {

    public static final String ERROR_INSERT = "商品规格组插入失败";
    public static final String ERROR_DELETE = "商品规格组删除失败";
    public static final String ERROR_UPDATE = "商品规格组更新失败";
    public static final String ERROR_ID = "商品规格组 ID 无效";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private GoodsSetMapper goodsSetMapper;
    @Resource
    private GoodsToSetService goodsToSetService;
    @Resource
    private GoodsChoiceService goodsChoiceService;

    private LambdaQueryWrapper<GoodsSet> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<GoodsSet> goodsSetLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                goodsSetLambdaQueryWrapper.select(GoodsSet::getSetId, GoodsSet::getShopId);
                break;
            case SELECT_BASE:
                goodsSetLambdaQueryWrapper.select(GoodsSet::getSetId, GoodsSet::getName, GoodsSet::getSort);
                break;
            case SELECT_INFO:
                goodsSetLambdaQueryWrapper.select(GoodsSet::getSetId, GoodsSet::getShopId, GoodsSet::getName, GoodsSet::getSort);
            default:
                break;
        }
        return goodsSetLambdaQueryWrapper;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0].goodsId"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0].goodsId")})
    public void insertGoodsSet(GoodsSet goodsSet, String shopId) {

        if (goodsSetMapper.insert(goodsSet) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_INSERT, shopId);
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0].goodsId"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0].goodsId")})
    public void deleteGoodsSet(GoodsSet goodsSet, String shopId) {

        if (goodsSetMapper.deleteById(goodsSet.getSetId()) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_DELETE, goodsSet.getSetId());
        }
        goodsChoiceService.deleteChoiceByGoodsSet(goodsSet.getSetId());
    }

    @Override
    @Async
    @Caching(evict = {
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0]"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0]")})
    public void deleteSetByGoods(String goodsId) {

        LambdaQueryWrapper<GoodsSet> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GoodsSet::getShopId, goodsId);
        List<GoodsSet> goodsSetList = goodsSetMapper.selectList(queryWrapper);

        goodsSetList.forEach(
                goodsSet -> {
                    goodsChoiceService.deleteChoiceByGoodsSet(goodsSet.getSetId());
                    if (goodsSetMapper.deleteById(goodsSet.getSetId()) <= 0) {
                        throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_DELETE, goodsSet.getSetId());
                    }
                }
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "goodsSet", key = "#root.args[0].setId"),
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0].goodsId"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0].goodsId")})
    public void updateGoodsSet(GoodsSet goodsSet, String shopId) {

        GoodsSet oldGoodsSet = getGoodsSet(goodsSet.getSetId());
        if (oldGoodsSet == null) {
            throw new GoodsException(ResultEnum.BAD_REQUEST, ERROR_ID, shopId);
        }

        oldGoodsSet.update(goodsSet);
        if (goodsSetMapper.updateById(oldGoodsSet) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, goodsSet.getSetId());
        }
    }

    @Override
    @Cacheable(cacheNames = "goodsSet", key = "#root.args[0]")
    public GoodsSet getGoodsSet(String setId) {
        return goodsSetMapper.selectById(setId);
    }

    @Override
    public List<GoodsSet> listGoodsSetBaseByGoods(String goodsId) {

        LambdaQueryWrapper<GoodsSet> goodsSetLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        List<String> setIdList = goodsToSetService.listSetIdByGoods(goodsId);
        if (setIdList.size() != 0) {
            goodsSetLambdaQueryWrapper.in(GoodsSet::getSetId, setIdList)
                    .orderByAsc(GoodsSet::getSort);
            return goodsSetMapper.selectList(goodsSetLambdaQueryWrapper);
        }
        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0]"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0]")})
    public List<GoodsSet> listGoodsSetBaseDetailByGoods(String goodsId) {

        List<GoodsSet> goodsSetList = listGoodsSetBaseByGoods(goodsId);

        if (goodsSetList != null) {
            goodsSetList.forEach(
                    goodsSet -> goodsSet.setGoodsChoiceList(goodsChoiceService.listGoodsChoiceBaseByGoodsSet(goodsSet.getSetId()))
            );
        }
        return goodsSetList;
    }
}
