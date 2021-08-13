package cool.sodo.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.ShopToCategory;
import cool.sodo.goods.mapper.ShopToCategoryMapper;
import cool.sodo.goods.service.ShopToCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShopToCategoryServiceImpl implements ShopToCategoryService {

    @Resource
    private ShopToCategoryMapper shopToCategoryMapper;

    @Override
    public List<String> listShopIdByCategory(String categoryId) {

        LambdaQueryWrapper<ShopToCategory> shopToCategoryLambdaQueryWrapper = Wrappers.lambdaQuery();
        shopToCategoryLambdaQueryWrapper.eq(ShopToCategory::getCategoryId, categoryId)
                .select(ShopToCategory::getShopId);
        return shopToCategoryMapper.selectList(shopToCategoryLambdaQueryWrapper)
                .stream().map(ShopToCategory::getShopId).collect(Collectors.toList());
    }

    @Override
    public List<ShopToCategory> listShopToCategoryByCategory(String categoryId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ShopToCategory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ShopToCategory::getCategoryId, categoryId);
        Page<ShopToCategory> page = new Page<>(pageNum, pageSize);

        return shopToCategoryMapper.selectPage(page, queryWrapper).getRecords();
    }
}
