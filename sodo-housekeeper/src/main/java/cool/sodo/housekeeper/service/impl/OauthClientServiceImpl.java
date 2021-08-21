package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.entity.OauthClientDTO;
import cool.sodo.housekeeper.mapper.OauthClientMapper;
import cool.sodo.housekeeper.service.ClientApiService;
import cool.sodo.housekeeper.service.OauthClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private OauthClientMapper oauthClientMapper;
    @Resource
    private ClientApiService clientApiService;
    @Resource
    private CommonUserService userService;

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
                        OauthClient::getCaptcha, OauthClient::getSignature, OauthClient::getUserStatus,
                        OauthClient::getTokenExpire, OauthClient::getRedirectUri, OauthClient::getCreateAt,
                        OauthClient::getCreateBy, OauthClient::getUpdateAt, OauthClient::getUpdateBy);
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
    public void updateOauthClient(OauthClient oauthClient, String userId) {

        if (StringUtil.isEmpty(oauthClient.getClientId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "ClientId 不能为空！");
        }

        OauthClient oauthClientOld = getOauthClient(oauthClient.getClientId());
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
    public void deleteOauthClient(String id, String userId) {

        OauthClient oauthClient = getOauthClient(id);
        oauthClient.delete(userId);
        updateOauthClient(oauthClient);
        if (oauthClientMapper.deleteById(id) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public OauthClient getOauthClient(String id) {

        OauthClient oauthClient = oauthClientMapper.selectById(id);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthClient;
    }

    @Override
    public OauthClient getOauthClientIdentity(String id) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, id);
        OauthClient oauthClient = oauthClientMapper.selectOne(oauthClientLambdaQueryWrapper);
        if (StringUtil.isEmpty(oauthClient)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return oauthClient;
    }

    @Override
    public OauthClient getOauthClientIdentityNullable(String id) {

        LambdaQueryWrapper<OauthClient> oauthClientLambdaQueryWrapper = generateSelectQueryWrapper(SELECT_IDENTITY);
        oauthClientLambdaQueryWrapper.eq(OauthClient::getClientId, id);
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
            oauthClient.setCreator(userService.getUserBase(oauthClient.getCreateBy()));
        }
        if (!StringUtil.isEmpty(oauthClient.getUpdateBy())) {
            oauthClient.setUpdater(userService.getUserBase(oauthClient.getUpdateBy()));
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

