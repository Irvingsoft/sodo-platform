package cool.sodo.zuul.service.impl;

import com.netflix.zuul.context.RequestContext;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.AccessToken;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.service.CommonAccessTokenService;
import cool.sodo.zuul.exception.AuthorizationException;
import cool.sodo.zuul.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Resource
    private CommonAccessTokenService accessTokenService;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    @Qualifier(value = "handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    /**
     * @param token    Token
     * @param clientId 客户端 ID
     * @return boolean
     * 验证 Token 是否存在、是否过期、是否属于该客户端，把未过期的 Token 放入 Redis，
     * @author TimeChaser
     * @date 2021/5/30 11:00
     */
    @Override
    public boolean validateAccessToken(String token, String clientId) {
        AccessToken accessToken;

        if (redisCacheHelper.hasKey(Constants.ACCESS_TOKEN_CACHE_PREFIX + token)) {
            accessToken = (AccessToken) redisCacheHelper.get(Constants.ACCESS_TOKEN_CACHE_PREFIX + token);
        } else {
            accessToken = accessTokenService.getAccessToken(token);
            if (StringUtils.isEmpty(accessToken)) {
                handlerExceptionResolver.resolveException(RequestContext.getCurrentContext().getRequest()
                        , RequestContext.getCurrentContext().getResponse()
                        , null
                        , new AuthorizationException(ResultEnum.UNAUTHORIZED));
            }
            if (accessToken.getExpireAt().getTime() >= System.currentTimeMillis()) {
                redisCacheHelper.set(Constants.ACCESS_TOKEN_CACHE_PREFIX + accessToken.getToken(), accessToken, Constants.ACCESS_TOKEN_CACHE_EXPIRE);
            }
        }

        return accessToken != null
                && accessToken.getExpireAt().getTime() >= System.currentTimeMillis()
                && accessToken.getClientId().equals(clientId);
    }

}
