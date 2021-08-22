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
import cool.sodo.common.util.WebUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 通用 OauthClient Service 层
 *
 * @author TimeChaser
 * @date 2021/7/13 10:20
 */
@Service
public class CommonOauthClientServiceImpl implements CommonOauthClientService {

    public static final String ERROR_SELECT = "OauthClient 不存在！";
    public static final String ERROR_CLIENT = "该客户端不允许注册！";

    @Resource
    private CommonOauthClientMapper commonOauthClientMapper;

    @Override
    public OauthClient getOauthClientIdentity(String clientId) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = Wrappers.lambdaQuery();
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, clientId)
                .select(OauthClient::getClientId, OauthClient::getClientSecret, OauthClient::getInUse,
                        OauthClient::getRegister, OauthClient::getCaptcha, OauthClient::getSignature, OauthClient::getUserStatus,
                        OauthClient::getTokenExpire, OauthClient::getRedirectUri, OauthClient::getUserStatus);
        OauthClient oauthClient = commonOauthClientMapper.selectOne(oauthClientLambdaQueryWrapper);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, clientId);
        }
        return oauthClient;
    }

    @Override
    public boolean validateOauthClientRegister(String clientId) {

        OauthClient oauthClient = commonOauthClientMapper.selectById(clientId);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT, clientId);
        }
        return oauthClient.getRegister();
    }

    @Override
    public void checkOauthClientRegister(HttpServletRequest request) {

        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        if (!validateOauthClientRegister(clientId)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_CLIENT, clientId);
        }
    }
}
