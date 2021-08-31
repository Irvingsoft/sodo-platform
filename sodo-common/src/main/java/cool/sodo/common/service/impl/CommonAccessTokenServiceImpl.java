package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.AccessToken;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonAccessTokenMapper;
import cool.sodo.common.service.CommonAccessTokenService;
import cool.sodo.common.util.StringUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class CommonAccessTokenServiceImpl implements CommonAccessTokenService {

    public static final String ERROR_INSERT = "AccessToken 新增失败！";
    public static final String ERROR_UPDATE = "AccessToken 更新失败！";
    public static final String ERROR_DELETE = "AccessToken 删除失败！";

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

        AccessToken accessToken = null;
        if (redisCacheHelper.hasKey(Constants.ACCESS_TOKEN_CACHE_PREFIX + token)) {
            accessToken = (AccessToken) redisCacheHelper.get(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
        } else {
            accessToken = get(token);
            if (accessToken.getExpireAt().getTime() >= System.currentTimeMillis()) {
                redisCacheHelper.set(Constants.ACCESS_TOKEN_CACHE_PREFIX + accessToken.getToken(),
                        accessToken,
                        TimeUnit.MILLISECONDS.toSeconds(accessToken.getExpireAt().getTime() - System.currentTimeMillis()));
            } else {
                throw new SoDoException(ResultEnum.INVALID_TOKEN, "令牌已过期，请重新登录获取新令牌！");
            }
        }
        return accessToken;
    }

    @Override
    @CacheEvict(cacheNames = Constants.ACCESS_TOKEN_CACHE_NAME, key = "#root.args[0].token")
    public void update(AccessToken accessToken) {

        if (commonAccessTokenMapper.updateById(accessToken) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    @CacheEvict(cacheNames = Constants.ACCESS_TOKEN_CACHE_NAME, key = "#root.args[0]")
    public void delete(String token) {

        if (commonAccessTokenMapper.deleteById(token) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void deleteByIdentity(String identity) {

        // TODO CACHE DELETE
        LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
        accessTokenLambdaQueryWrapper.eq(AccessToken::getIdentity, identity);
        if (commonAccessTokenMapper.delete(accessTokenLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void insert(AccessToken accessToken) {

        if (commonAccessTokenMapper.insert(accessToken) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
    }
}
