package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.domain.GoodsChoice;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.goods.exception.GoodsException;
import cool.sodo.goods.mapper.GoodsChoiceMapper;
import cool.sodo.goods.service.GoodsChoiceService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsChoiceServiceImpl implements GoodsChoiceService {

    public static final String ERROR_INSERT = "商品选项新增失败";
    public static final String ERROR_DELETE = "商品选项删除失败";
    public static final String ERROR_UPDATE = "商品选项更新失败";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private GoodsChoiceMapper goodsChoiceMapper;

    private LambdaQueryWrapper<GoodsChoice> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<GoodsChoice> goodsChoiceLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                goodsChoiceLambdaQueryWrapper.select(GoodsChoice::getChoiceId, GoodsChoice::getShopId, GoodsChoice::getSetId);
                break;
            case SELECT_BASE:
                goodsChoiceLambdaQueryWrapper.select(GoodsChoice::getChoiceId, GoodsChoice::getName, GoodsChoice::getSort);
                break;
            case SELECT_INFO:
            default:
                break;
        }
        return goodsChoiceLambdaQueryWrapper;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0].goodsId"),
            @CacheEvict(cacheNames = "listChoice", key = "#root.args[0].setId"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0].goodsId")})
    public void insertGoodsChoice(GoodsChoice goodsChoice, String shopId) {

        if (goodsChoiceMapper.insert(goodsChoice) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_INSERT, goodsChoice.getShopId());
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0].goodsId"),
            @CacheEvict(cacheNames = "goodsChoice", key = "#root.args[0].choiceId"),
            @CacheEvict(cacheNames = "listChoice", key = "#root.args[0].setId"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0].goodsId")})
    public void deleteGoodsChoice(GoodsChoice goodsChoice, String shopId) {

        if (goodsChoiceMapper.deleteById(goodsChoice.getChoiceId()) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_DELETE, goodsChoice.getChoiceId());
        }
    }

    @Override
    @CacheEvict(cacheNames = "goodsChoice", key = "#root.args[0]")
    public void deleteGoodsChoice(String choiceId) {

        if (goodsChoiceMapper.deleteById(choiceId) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_DELETE, choiceId);
        }
    }

    @Override
    @Async
    @CacheEvict(cacheNames = "listChoice", key = "#root.args[0]")
    public void deleteChoiceByGoodsSet(String setId) {

        LambdaQueryWrapper<GoodsChoice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GoodsChoice::getSetId, setId);
        List<GoodsChoice> goodsChoiceList = goodsChoiceMapper.selectList(queryWrapper);

        goodsChoiceList.forEach(
                goodsChoice -> deleteGoodsChoice(goodsChoice.getChoiceId())
        );
    }

    /**
     * Controller 中调用个一次 getGoodsChoice 方法，此方法中再次调用
     * 由于缓存清除是在方法执行完后再清除缓存，所以必有一次调用 getGoodsChoice 方法走的是缓存
     *
     * @author TimeChaser
     * @date 2020/12/1 10:02 上午
     */
    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "listSetDetail", key = "#root.args[0].goodsId"),
            @CacheEvict(cacheNames = "goodsChoice", key = "#root.args[0].choiceId"),
            @CacheEvict(cacheNames = "listChoice", key = "#root.args[0].setId"),
            @CacheEvict(cacheNames = "goods", key = "#root.args[0].goodsId")})
    public void updateGoodsChoice(GoodsChoice goodsChoice, String shopId) {

        GoodsChoice oldGoodsChoice = getGoodsChoice(goodsChoice.getChoiceId());

        oldGoodsChoice.update(goodsChoice);
        if (goodsChoiceMapper.updateById(oldGoodsChoice) <= 0) {
            throw new GoodsException(ResultEnum.SERVER_ERROR, ERROR_UPDATE, goodsChoice.getShopId());
        }
    }

    @Override
    @Cacheable(cacheNames = "goodsChoice", key = "#root.args[0]")
    public GoodsChoice getGoodsChoice(String choiceId) {

        return goodsChoiceMapper.selectById(choiceId);
    }

    @Override
    @Cacheable(cacheNames = "listChoice", key = "#root.args[0]")
    public List<GoodsChoice> listGoodsChoiceBaseByGoodsSet(String setId) {

        LambdaQueryWrapper<GoodsChoice> goodsChoiceLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        goodsChoiceLambdaQueryWrapper.eq(GoodsChoice::getSetId, setId)
                .orderByAsc(GoodsChoice::getSort);
        return goodsChoiceMapper.selectList(goodsChoiceLambdaQueryWrapper);
    }
}
