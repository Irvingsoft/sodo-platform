package cool.sodo.zuul.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.OauthIp;
import cool.sodo.common.publisher.OauthIpCheckPublisher;
import cool.sodo.zuul.mapper.OauthIpMapper;
import cool.sodo.zuul.service.OauthIpService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OauthIpServiceImpl implements OauthIpService {

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;

    @Resource
    private OauthIpMapper oauthIpMapper;

    private LambdaQueryWrapper<OauthIp> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getIp);
                break;
            case SELECT_BASE:
                oauthIpLambdaQueryWrapper.select(OauthIp::getIpId, OauthIp::getValidNum);
                break;
            default:
                break;
        }

        return oauthIpLambdaQueryWrapper;
    }

    @Override
    public boolean validOauthIp(String clientId, String ip) {

        if (StringUtils.isEmpty(ip)) {
            return false;
        }

        List<OauthIp> oauthIpValidList = listOauthIpValidIdentityByClient(clientId);
        if (oauthIpValidList.size() != 0) {
            for (OauthIp oauthIp : oauthIpValidList) {
                if (oauthIp.getIp().equals(ip)) {
                    OauthIpCheckPublisher.publishEvent(oauthIp.getIpId());
                    return true;
                }
            }
            return false;
        }

        List<OauthIp> oauthIpNotValidList = listOauthIpNotValidIdentityByClient(clientId);
        if (oauthIpNotValidList.size() != 0) {
            for (OauthIp oauthIp : oauthIpNotValidList) {
                if (oauthIp.getIp().equals(ip)) {
                    OauthIpCheckPublisher.publishEvent(oauthIp.getIpId());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<OauthIp> listOauthIpValidIdentityByClient(String clientId) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthIpLambdaQueryWrapper.eq(OauthIp::getClientId, clientId)
                .eq(OauthIp::getValid, true);
        return oauthIpMapper.selectList(oauthIpLambdaQueryWrapper);
    }

    @Override
    public List<OauthIp> listOauthIpNotValidIdentityByClient(String clientId) {

        LambdaQueryWrapper<OauthIp> oauthIpLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthIpLambdaQueryWrapper.eq(OauthIp::getClientId, clientId)
                .eq(OauthIp::getValid, false);
        return oauthIpMapper.selectList(oauthIpLambdaQueryWrapper);
    }
}
