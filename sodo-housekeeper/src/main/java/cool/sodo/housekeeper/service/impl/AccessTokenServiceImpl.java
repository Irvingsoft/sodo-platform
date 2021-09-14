package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.core.component.RedisCacheHelper;
import cool.sodo.common.core.domain.AccessToken;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.core.mapper.CommonAccessTokenMapper;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.housekeeper.service.AccessTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TimeChaser
 * @date 2021/9/1 23:13
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Resource
    private CommonAccessTokenMapper accessTokenMapper;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    @Override
    public void deleteCacheByClient(String clientId) {

        List<String> tokenList = listTokenByClient(clientId);
        if (!StringUtil.isEmpty(tokenList)) {
            for (String token : tokenList) {
                redisCacheHelper.delete(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
            }
            LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
            accessTokenLambdaQueryWrapper.in(AccessToken::getToken, tokenList);
            if (accessTokenMapper.delete(accessTokenLambdaQueryWrapper) < 0) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "AccessToken 删除失败！");
            }
        }
    }

    @Override
    public void deleteCacheByIdentity(String identity) {

        List<String> tokenList = listTokenByIdentity(identity);
        if (!StringUtil.isEmpty(tokenList)) {
            for (String token : tokenList) {
                redisCacheHelper.delete(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
            }
            LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
            accessTokenLambdaQueryWrapper.in(AccessToken::getToken, tokenList);
            if (accessTokenMapper.delete(accessTokenLambdaQueryWrapper) < 0) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, "AccessToken 删除失败！");
            }
        }
    }

    @Override
    public List<String> listTokenByIdentity(String identity) {

        LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
        accessTokenLambdaQueryWrapper.eq(AccessToken::getIdentity, identity)
                .select(AccessToken::getToken);
        return accessTokenMapper.selectList(accessTokenLambdaQueryWrapper)
                .stream()
                .map(AccessToken::getToken)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listTokenByClient(String clientId) {

        LambdaQueryWrapper<AccessToken> accessTokenLambdaQueryWrapper = Wrappers.lambdaQuery();
        accessTokenLambdaQueryWrapper.eq(AccessToken::getClientId, clientId)
                .select(AccessToken::getToken);
        return accessTokenMapper.selectList(accessTokenLambdaQueryWrapper)
                .stream()
                .map(AccessToken::getToken)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean online(User user) {

        List<String> tokenList = listTokenByIdentity(user.getUsername());
        tokenList.addAll(listTokenByIdentity(user.getOpenId()));
        for (String token : tokenList) {
            if (redisCacheHelper.hasKey(Constants.ACCESS_TOKEN_CACHE_PREFIX + token)) {
                return true;
            }
        }
        return false;
    }
}
