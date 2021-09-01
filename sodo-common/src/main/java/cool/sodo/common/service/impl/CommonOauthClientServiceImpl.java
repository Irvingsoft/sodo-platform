package cool.sodo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.mapper.CommonOauthClientMapper;
import cool.sodo.common.service.CommonOauthClientService;
import cool.sodo.common.util.StringUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 通用 OauthClient Service 层
 *
 * @author TimeChaser
 * @date 2021/7/13 10:20
 */
@Service
public class CommonOauthClientServiceImpl implements CommonOauthClientService {

    public static final String ERROR_SELECT = "OauthClient 不存在！";

    @Resource
    private CommonOauthClientMapper commonOauthClientMapper;

    @Override
    @Cacheable(cacheNames = Constants.OAUTH_CLIENT_CACHE_NAME, key = "#clientId")
    public OauthClient getOauthClientIdentity(String clientId) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = Wrappers.lambdaQuery();
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, clientId)
                .select(OauthClient::getClientId, OauthClient::getClientSecret, OauthClient::getInUse,
                        OauthClient::getRegister, OauthClient::getCaptcha, OauthClient::getSignature,
                        OauthClient::getConcurrentLogin, OauthClient::getShareToken, OauthClient::getUserStatus,
                        OauthClient::getTokenExpire, OauthClient::getRedirectUri, OauthClient::getUserStatus);
        OauthClient oauthClient = commonOauthClientMapper.selectOne(oauthClientLambdaQueryWrapper);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, clientId);
        }
        return oauthClient;
    }
}
