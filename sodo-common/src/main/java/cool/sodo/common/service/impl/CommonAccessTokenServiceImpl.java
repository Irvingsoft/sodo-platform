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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class CommonAccessTokenServiceImpl implements CommonAccessTokenService {

    public static final String ERROR_INSERT = "AccessToken 新增失败！";
    public static final String ERROR_UPDATE = "AccessToken 更新失败！";
    public static final String ERROR_DELETE = "AccessToken 删除失败！";

    public static final String INVALID_TOKEN = "请重新登录获取新的令牌！";
    public static final String ERROR_TOKEN_CLIENT = "登录源与请求源不一致！";

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
    public AccessToken getAccessTokenNullableByIdentity(String identity) {

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
    public AccessToken getAccessToken(String token) {

        AccessToken accessToken = commonAccessTokenMapper.selectById(token);
        if (StringUtils.isEmpty(accessToken)) {
            throw new SoDoException(ResultEnum.INVALID_TOKEN, INVALID_TOKEN, token);
        }
        return accessToken;
    }

    @Override
    public AccessToken getAccessTokenCache(String token) {

        AccessToken accessToken = null;
        if (redisCacheHelper.hasKey(Constants.ACCESS_TOKEN_CACHE_PREFIX + token)) {
            accessToken = (AccessToken) redisCacheHelper.get(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
        } else {
            accessToken = getAccessToken(token);
            if (accessToken.getExpireAt().getTime() >= System.currentTimeMillis()) {
                redisCacheHelper.set(Constants.ACCESS_TOKEN_CACHE_PREFIX + accessToken.getToken(),
                        accessToken,
                        TimeUnit.MILLISECONDS.toSeconds(accessToken.getExpireAt().getTime() - System.currentTimeMillis()));
            }
        }
        return accessToken;
    }

    @Override
    @CacheEvict(cacheNames = Constants.ACCESS_TOKEN_CACHE_NAME, key = "#root.args[0].token")
    public void updateAccessToken(AccessToken accessToken) {

        if (commonAccessTokenMapper.updateById(accessToken) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    @CacheEvict(cacheNames = Constants.ACCESS_TOKEN_CACHE_NAME, key = "#root.args[0]")
    public void removeAccessTokenByToken(String token) {

        if (commonAccessTokenMapper.deleteById(token) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void insertAccessToken(AccessToken accessToken) {

        if (commonAccessTokenMapper.insert(accessToken) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
    }

    @Override
    public void checkAccessToken(AccessToken accessToken, String clientId) {

        if (StringUtils.isEmpty(accessToken) || accessToken.getExpireAt().getTime() < System.currentTimeMillis()) {
            throw new SoDoException(ResultEnum.INVALID_TOKEN, INVALID_TOKEN);
        }
        if (!accessToken.getClientId().equals(clientId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_TOKEN_CLIENT);
        }
    }

    @Override
    public boolean validateAccessToken(String token) {
        return redisCacheHelper.hasKey(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
    }
}
