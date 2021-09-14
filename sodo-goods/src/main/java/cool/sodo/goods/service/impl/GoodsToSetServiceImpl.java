package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.starter.domain.GoodsToSet;
import cool.sodo.goods.mapper.GoodsToSetMapper;
import cool.sodo.goods.service.GoodsToSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsToSetServiceImpl implements GoodsToSetService {

    @Resource
    private GoodsToSetMapper goodsToSetMapper;

    @Override
    public List<String> listSetIdByGoods(String goodsId) {

        LambdaQueryWrapper<GoodsToSet> goodsToSetLambdaQueryWrapper = Wrappers.lambdaQuery();
        goodsToSetLambdaQueryWrapper.select(GoodsToSet::getSetId)
                .eq(GoodsToSet::getGoodsId, goodsId);
        List<GoodsToSet> goodsToSetList = goodsToSetMapper.selectList(goodsToSetLambdaQueryWrapper);
        return goodsToSetList.stream().map(GoodsToSet::getSetId).collect(Collectors.toList());
    }
}
