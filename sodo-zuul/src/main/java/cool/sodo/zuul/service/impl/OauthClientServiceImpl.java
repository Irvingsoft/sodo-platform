package cool.sodo.zuul.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.util.StringUtil;
import cool.sodo.zuul.mapper.OauthClientMapper;
import cool.sodo.zuul.service.OauthClientService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OauthClientServiceImpl implements OauthClientService {

    public static final String ERROR_SELECT = "客户端不存在！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;

    @Resource
    private OauthClientMapper oauthClientMapper;

    private LambdaQueryWrapper<OauthClient> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                oauthClientLambdaQueryWrapper.select(OauthClient::getClientId, OauthClient::getClientSecret
                        , OauthClient::getInUse, OauthClient::getRegister, OauthClient::getCaptcha);
                break;
            case SELECT_BASE:
            default:
                break;
        }
        return oauthClientLambdaQueryWrapper;
    }

    @Override
    public boolean validateClient(String clientId) {

        if (StringUtil.isEmpty(clientId)) {
            return false;
        }
        return getOauthClientIdentity(clientId).getInUse();
    }

    @Override
    @Cacheable(cacheNames = "client", key = "#root.args[0]")
    public OauthClient getOauthClientIdentity(String clientId) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, clientId);
        OauthClient oauthClient = oauthClientMapper.selectOne(oauthClientLambdaQueryWrapper);

        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.INVALID_CLIENT, ERROR_SELECT, clientId);
        }
        return oauthClient;
    }
}
