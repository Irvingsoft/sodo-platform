package cool.sodo.catkin.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.catkin.server.domain.CatkinInfo;
import cool.sodo.catkin.server.mapper.CatkinInfoMapper;
import cool.sodo.catkin.server.service.CatkinInfoService;
import cool.sodo.common.base.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TimeChaser
 * @date 2021/9/12 17:47
 */
@Service
public class CatkinInfoServiceImpl implements CatkinInfoService {

    @Resource
    private CatkinInfoMapper catkinInfoMapper;

    @Override
    public int updateMaxId(Long id, Long newMaxId, Long oldMaxId, Long version, String bizType) {

        LambdaUpdateWrapper<CatkinInfo> catkinInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        catkinInfoLambdaUpdateWrapper.eq(CatkinInfo::getId, id)
                .eq(CatkinInfo::getMaxId, oldMaxId)
                .eq(CatkinInfo::getVersion, version)
                .eq(CatkinInfo::getBizType, bizType)
                .set(CatkinInfo::getMaxId, newMaxId);
        return catkinInfoMapper.update(null, catkinInfoLambdaUpdateWrapper);
    }

    @Override
    public CatkinInfo getOneByBizType(String bizType) {

        LambdaQueryWrapper<CatkinInfo> catkinInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        catkinInfoLambdaQueryWrapper.eq(CatkinInfo::getBizType, bizType);
        List<CatkinInfo> catkinInfos = catkinInfoMapper.selectList(catkinInfoLambdaQueryWrapper);
        if (StringUtil.isEmpty(catkinInfos)) {
            return null;
        }
        return catkinInfos.get(0);
    }
}
