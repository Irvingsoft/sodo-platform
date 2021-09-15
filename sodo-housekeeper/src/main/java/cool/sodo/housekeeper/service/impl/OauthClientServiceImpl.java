package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.domain.OauthClient;
import cool.sodo.common.core.mapper.CommonOauthClientMapper;
import cool.sodo.common.core.service.CommonUserService;
import cool.sodo.housekeeper.entity.OauthClientDTO;
import cool.sodo.housekeeper.service.AccessTokenService;
import cool.sodo.housekeeper.service.ClientApiService;
import cool.sodo.housekeeper.service.OauthClientService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TimeChaser
 * @date 2021/9/1 23:53
 */
@Service
public class OauthClientServiceImpl implements OauthClientService {

    public static final String ERROR_INSERT = "OauthClient 插入失败！";
    public static final String ERROR_DELETE = "OauthClient 删除失败！";
    public static final String ERROR_UPDATE = "OauthClient 更新失败！";
    public static final String ERROR_SELECT = "OauthClient 不存在！";

    public static final int SELECT_IDENTITY = 0;
    public static final int SELECT_BASE = 1;
    public static final int SELECT_INFO = 2;

    @Resource
    private CommonOauthClientMapper oauthClientMapper;
    @Resource
    private ClientApiService clientApiService;
    @Resource
    private CommonUserService userService;
    @Resource
    private AccessTokenService accessTokenService;

    private LambdaQueryWrapper<OauthClient> generateSelectQueryWrapper(int type) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = Wrappers.lambdaQuery();

        switch (type) {
            case SELECT_IDENTITY:
                oauthClientLambdaQueryWrapper.select(OauthClient::getClientId,
                        OauthClient::getClientSecret, OauthClient::getInUse);
                break;
            case SELECT_BASE:
                oauthClientLambdaQueryWrapper.select(OauthClient::getClientId,
                        OauthClient::getName, OauthClient::getDescription, OauthClient::getInUse);
                break;
            case SELECT_INFO:
                oauthClientLambdaQueryWrapper.select(OauthClient::getClientId, OauthClient::getClientSecret,
                        OauthClient::getName, OauthClient::getDescription, OauthClient::getInUse, OauthClient::getRegister,
                        OauthClient::getCaptcha, OauthClient::getSignature, OauthClient::getConcurrentLogin,
                        OauthClient::getShareToken, OauthClient::getUserStatus, OauthClient::getTokenExpire,
                        OauthClient::getRedirectUri, OauthClient::getCreateAt, OauthClient::getCreateBy,
                        OauthClient::getUpdateAt, OauthClient::getUpdateBy);
                break;
            default:
                break;
        }
        return oauthClientLambdaQueryWrapper;
    }

    @Override
    public void insertOauthClient(OauthClient oauthClient, String userId) {

        if (!StringUtil.isEmpty(oauthClient.getClientId())
                && !StringUtil.isEmpty(getOauthClientIdentityNullable(oauthClient.getClientId()))) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "OauthApi 已存在！");
        }

        oauthClient.init(userId);
        if (oauthClientMapper.insert(oauthClient) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
        clientApiService.insertClientApiOfOauthClient(oauthClient.getClientId(), oauthClient.getApiIdList(), userId);
    }

    @Override
    @CacheEvict(cacheNames = Constants.OAUTH_CLIENT_CACHE_NAME, key = "#oauthClient.clientId")
    public void updateOauthClient(OauthClient oauthClient, String userId) {

        if (StringUtil.isEmpty(oauthClient.getClientId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "ClientId 不能为空！");
        }

        OauthClient oauthClientOld = getOauthClient(oauthClient.getClientId());
        /*
          1. 并发登录下，开启共享 Token
          2. 关闭并发登录
         */
        if (!oauthClientOld.getShareToken().equals(oauthClient.getShareToken())
                && oauthClient.getConcurrentLogin()
                && oauthClient.getShareToken()) {
            accessTokenService.deleteCacheByClient(oauthClient.getClientId());
        }
        if (!oauthClientOld.getConcurrentLogin().equals(oauthClient.getConcurrentLogin())
                && !oauthClient.getConcurrentLogin()) {
            accessTokenService.deleteCacheByClient(oauthClient.getClientId());
        }
        oauthClientOld.update(oauthClient, userId);
        if (oauthClientMapper.updateById(oauthClientOld) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
        clientApiService.updateClientApiOfOauthClient(oauthClient.getClientId(), oauthClient.getApiIdList(), oauthClient.getUpdateBy());
    }

    @Override
    public void updateOauthClient(OauthClient oauthClient) {
        if (oauthClientMapper.updateById(oauthClient) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_UPDATE);
        }
    }

    @Override
    @CacheEvict(cacheNames = Constants.OAUTH_CLIENT_CACHE_NAME, key = "#clientId")
    public void deleteOauthClient(String clientId, String userId) {

        OauthClient oauthClient = getOauthClient(clientId);
        oauthClient.delete(userId);
        updateOauthClient(oauthClient);
        if (oauthClientMapper.deleteById(clientId) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public OauthClient getOauthClient(String clientId) {

        OauthClient oauthClient = oauthClientMapper.selectById(clientId);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthClient;
    }

    @Override
    public OauthClient getOauthClientIdentity(String clientId) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, clientId);
        OauthClient oauthClient = oauthClientMapper.selectOne(oauthClientLambdaQueryWrapper);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthClient;
    }

    @Override
    public OauthClient getOauthClientIdentityNullable(String clientId) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, clientId);
        return oauthClientMapper.selectOne(oauthClientLambdaQueryWrapper);
    }

    @Override
    public OauthClient getOauthClientInfoDetail(String clientId) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, clientId);
        OauthClient oauthClient = oauthClientMapper.selectOne(oauthClientLambdaQueryWrapper);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        if (!StringUtil.isEmpty(oauthClient.getCreateBy())) {
            oauthClient.setCreator(userService.getBase(oauthClient.getCreateBy()));
        }
        if (!StringUtil.isEmpty(oauthClient.getUpdateBy())) {
            oauthClient.setUpdater(userService.getBase(oauthClient.getUpdateBy()));
        }
        oauthClient.setApiIdList(clientApiService.listClientApiApiId(clientId));
        return oauthClient;
    }

    @Override
    public IPage<OauthClient> pageOauthClientInfo(OauthClientDTO oauthClientDTO) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_INFO);
        if (!StringUtil.isEmpty(oauthClientDTO.getInUse())) {
            oauthClientLambdaQueryWrapper.eq(OauthClient::getInUse, oauthClientDTO.getInUse());
        }
        if (!StringUtil.isEmpty(oauthClientDTO.getRegister())) {
            oauthClientLambdaQueryWrapper.eq(OauthClient::getRegister, oauthClientDTO.getRegister());
        }
        if (!StringUtil.isEmpty(oauthClientDTO.getCaptcha())) {
            oauthClientLambdaQueryWrapper.eq(OauthClient::getCaptcha, oauthClientDTO.getCaptcha());
        }
        if (!StringUtil.isEmpty(oauthClientDTO.getContent())) {
            oauthClientLambdaQueryWrapper.and(wrapper -> wrapper.like(OauthClient::getName, oauthClientDTO.getContent())
                    .or()
                    .like(OauthClient::getDescription, oauthClientDTO.getContent()));
        }
        return oauthClientMapper.selectPage(
                new Page<>(oauthClientDTO.getPageNum(), oauthClientDTO.getPageSize()),
                oauthClientLambdaQueryWrapper);
    }

    @Override
    public List<OauthClient> listOauthClientBaseUse() {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_BASE);
        oauthClientLambdaQueryWrapper.eq(OauthClient::getInUse, true);
        return oauthClientMapper.selectList(oauthClientLambdaQueryWrapper);
    }
}

