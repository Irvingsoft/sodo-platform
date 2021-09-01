package cool.sodo.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.auth.service.AccessTokenService;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.AccessToken;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonAccessTokenMapper;
import cool.sodo.common.service.impl.CommonAccessTokenServiceImpl;
import cool.sodo.common.util.StringUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TimeChaser
 * @date 2021/9/1 22:56
 */
@Service
@Primary
public class AccessTokenServiceImpl extends CommonAccessTokenServiceImpl implements AccessTokenService {

    public static final String ERROR_INSERT = "AccessToken 新增失败！";
    public static final String ERROR_UPDATE = "AccessToken 更新失败！";
    public static final String ERROR_DELETE = "AccessToken 删除失败！";

    @Resource
    private CommonAccessTokenMapper accessTokenMapper;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    @Override
    public void insert(AccessToken accessToken) {
        if (accessTokenMapper.insert(accessToken) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
    }

    @Override
    @CacheEvict(cacheNames = Constants.ACCESS_TOKEN_CACHE_NAME, key = "#token")
    public void delete(String token) {

        if (accessTokenMapper.deleteById(token) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void deleteCacheByIdentity(String identity) {

        List<String> tokenList = listToken(identity);
        if (!StringUtil.isEmpty(tokenList)) {
            for (String token : tokenList) {
                redisCacheHelper.delete(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
            }
            LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
            accessTokenLambdaQueryWrapper.in(AccessToken::getToken, tokenList);
            if (accessTokenMapper.delete(accessTokenLambdaQueryWrapper) < 0) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
            }
        }
    }

    @Override
    @CacheEvict(cacheNames = Constants.ACCESS_TOKEN_CACHE_NAME, key = "#accessToken.token")
    public void update(AccessToken accessToken) {

        if (accessTokenMapper.updateById(accessToken) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    public AccessToken getByIdentity(String identity) {
        return super.getByIdentity(identity);
    }

    @Override
    public List<String> listToken(String identity) {

        LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
        accessTokenLambdaQueryWrapper.eq(AccessToken::getIdentity, identity)
                .select(AccessToken::getToken);
        return accessTokenMapper.selectList(accessTokenLambdaQueryWrapper)
                .stream()
                .map(AccessToken::getToken)
                .collect(Collectors.toList());
    }
}
