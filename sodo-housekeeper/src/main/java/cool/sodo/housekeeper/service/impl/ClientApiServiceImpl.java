package cool.sodo.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.domain.ClientApi;
import cool.sodo.common.core.mapper.CommonClientApiMapper;
import cool.sodo.housekeeper.service.ClientApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientApiServiceImpl implements ClientApiService {

    public static final String ERROR_INSERT = "ClientApi 新增失败！";
    public static final String ERROR_DELETE = "ClientApi 删除失败！";
    public static final String ERROR_SELECT = "ClientApi 不存在！";

    @Resource
    private CommonClientApiMapper clientApiMapper;

    @Override
    public void insertClientApi(ClientApi clientApi) {

        if (clientApiMapper.insert(clientApi) <= 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT);
        }
    }

    @Override
    public void insertClientApi(List<ClientApi> clientApiList) {

        for (ClientApi clientApi : clientApiList) {
            LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
            clientApiLambdaQueryWrapper.eq(ClientApi::getClientId, clientApi.getClientId())
                    .eq(ClientApi::getApiId, clientApi.getApiId());
            if (clientApiMapper.selectCount(clientApiLambdaQueryWrapper) == 0) {
                insertClientApi(clientApi);
            }
        }
    }

    @Override
    public void insertClientApiOfOauthApi(String apiId, List<String> clientIdList, String userId) {

        if (StringUtil.isEmpty(apiId) || StringUtil.isEmpty(clientIdList)) {
            return;
        }
        for (String clientId : clientIdList) {
            insertClientApi(new ClientApi(clientId, apiId, userId));
        }
    }

    @Override
    public void insertClientApiOfOauthClient(String clientId, List<String> apiIdList, String userId) {
        if (StringUtil.isEmpty(clientId) || StringUtil.isEmpty(apiIdList)) {
            return;
        }
        for (String apiId : apiIdList) {
            insertClientApi(new ClientApi(clientId, apiId, userId));
        }
    }

    @Override
    public void deleteClientApiByOauthApi(String apiId) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        clientApiLambdaQueryWrapper.eq(ClientApi::getApiId, apiId);
        if (clientApiMapper.delete(clientApiLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void deleteClientApiByOauthApi(List<String> apiIdList) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        clientApiLambdaQueryWrapper.in(ClientApi::getApiId, apiIdList);
        if (clientApiMapper.delete(clientApiLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void deleteClientApiByOauthClient(String clientId) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientApiLambdaQueryWrapper.eq(ClientApi::getClientId, clientId);
        if (clientApiMapper.delete(clientApiLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void deleteClientApiByOauthClientAndNotInOauthApi(String clientId, List<String> apiIdList) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientApiLambdaQueryWrapper.eq(ClientApi::getClientId, clientId)
                .notIn(ClientApi::getApiId, apiIdList);
        if (clientApiMapper.delete(clientApiLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void deleteClientApiByOauthApiAndNotInOauthClient(String apiId, List<String> clientIdList) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientApiLambdaQueryWrapper.eq(ClientApi::getApiId, apiId)
                .notIn(ClientApi::getClientId, clientIdList);
        if (clientApiMapper.delete(clientApiLambdaQueryWrapper) < 0) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_DELETE);
        }
    }

    @Override
    public void updateClientApiOfOauthClient(String clientId, List<String> apiIdList, String userId) {

        if (StringUtil.isEmpty(apiIdList)) {
            deleteClientApiByOauthClient(clientId);
            return;
        }
        deleteClientApiByOauthClientAndNotInOauthApi(clientId, apiIdList);
        apiIdList.forEach(
                apiId -> {
                    LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    clientApiLambdaQueryWrapper.eq(ClientApi::getClientId, clientId)
                            .eq(ClientApi::getApiId, apiId);
                    if (clientApiMapper.selectCount(clientApiLambdaQueryWrapper) == 0) {
                        insertClientApi(new ClientApi(clientId, apiId, userId));
                    }
                }
        );
    }

    @Override
    public void updateClientApiOfOauthApi(String apiId, List<String> clientIdList, String userId) {

        if (StringUtil.isEmpty(clientIdList)) {
            deleteClientApiByOauthApi(apiId);
            return;
        }
        deleteClientApiByOauthApiAndNotInOauthClient(apiId, clientIdList);
        clientIdList.forEach(
                clientId -> {
                    LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    clientApiLambdaQueryWrapper.eq(ClientApi::getClientId, clientId)
                            .eq(ClientApi::getApiId, apiId);
                    if (clientApiMapper.selectCount(clientApiLambdaQueryWrapper) == 0) {
                        insertClientApi(new ClientApi(clientId, apiId, userId));
                    }
                }
        );
    }

    @Override
    public ClientApi getClientApiIdentity(String id) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        clientApiLambdaQueryWrapper.eq(ClientApi::getId, id)
                .select(ClientApi::getId, ClientApi::getClientId, ClientApi::getApiId);
        ClientApi clientApi = clientApiMapper.selectOne(clientApiLambdaQueryWrapper);
        if (StringUtil.isEmpty(clientApi)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_SELECT);
        }
        return clientApi;
    }

    @Override
    public ClientApi getClientApiIdentityNullable(String id) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        clientApiLambdaQueryWrapper.eq(ClientApi::getId, id)
                .select(ClientApi::getId, ClientApi::getClientId, ClientApi::getApiId);
        return clientApiMapper.selectOne(clientApiLambdaQueryWrapper);
    }

    @Override
    public List<String> listClientApiApiId(String clientId) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        clientApiLambdaQueryWrapper.eq(ClientApi::getClientId, clientId)
                .select(ClientApi::getApiId);
        return clientApiMapper.selectList(clientApiLambdaQueryWrapper).stream().map(ClientApi::getApiId).collect(Collectors.toList());
    }

    @Override
    public List<String> listClientApiClientId(String apiId) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        clientApiLambdaQueryWrapper.eq(ClientApi::getApiId, apiId)
                .select(ClientApi::getClientId);
        return clientApiMapper.selectList(clientApiLambdaQueryWrapper).stream().map(ClientApi::getClientId).collect(Collectors.toList());
    }
}
