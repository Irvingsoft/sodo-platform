package cool.sodo.common.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.domain.AccessToken;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.mapper.CommonAccessTokenMapper;
import cool.sodo.common.base.service.CommonAccessTokenService;
import cool.sodo.common.base.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/1 22:55
 */
@Service
public class CommonAccessTokenServiceImpl implements CommonAccessTokenService {

    public static final String INVALID_TOKEN = "请重新登录获取新的令牌！";

    @Resource
    private CommonAccessTokenMapper commonAccessTokenMapper;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    /**
     * 不抛出异常，由调用方校验
     *
     * @author TimeChaser
     * @date 2021/6/1 11:58
     */
    @Override
    public AccessToken getByIdentity(String identity) {

        LambdaQueryWrapper<AccessToken> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AccessToken::getIdentity, identity);
        return commonAccessTokenMapper.selectOne(queryWrapper);
    }

    /**
     * 不抛出异常，由调用方校验
     *
     * @author TimeChaser
     * @date 2021/6/1 11:58
     */
    @Override
    public AccessToken get(String token) {

        AccessToken accessToken = commonAccessTokenMapper.selectById(token);
        if (StringUtil.isEmpty(accessToken)) {
            throw new SoDoException(ResultEnum.INVALID_TOKEN, INVALID_TOKEN, token);
        }
        return accessToken;
    }

    @Override
    public AccessToken getFromCache(String token) {
        return (AccessToken) redisCacheHelper.get(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
    }
}
