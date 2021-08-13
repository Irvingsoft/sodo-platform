package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.OauthIp;
import cool.sodo.common.exception.AsyncException;
import cool.sodo.common.mapper.CommonOauthIpMapper;
import cool.sodo.common.service.CommonOauthIpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonOauthIpServiceImpl implements CommonOauthIpService {

    public static final String ERROR_UPDATE = "异步任务更新 OauthIp 访问数据失败！";

    @Resource
    private CommonOauthIpMapper commonOauthIpMapper;

    @Override
    public void updateOauthIpValidNumByAsync(String ipId) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = Wrappers.lambdaQuery();
        oauthIpLambdaQueryWrapper.select(OauthIp::getValidNum)
                .eq(OauthIp::getIpId, ipId);
        OauthIp oauthIp = commonOauthIpMapper.selectOne(oauthIpLambdaQueryWrapper);
        LambdaUpdateWrapper<OauthIp> oauthIpLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        oauthIpLambdaUpdateWrapper.eq(OauthIp::getIpId, ipId)
                .set(OauthIp::getValidNum, oauthIp.getValidNum() + 1);
        if (commonOauthIpMapper.update(null, oauthIpLambdaUpdateWrapper) <= 0) {
            throw new AsyncException(ERROR_UPDATE);
        }
    }
}
