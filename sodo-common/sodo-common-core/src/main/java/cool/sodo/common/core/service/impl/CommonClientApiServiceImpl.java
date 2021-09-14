package cool.sodo.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.core.domain.ClientApi;
import cool.sodo.common.core.mapper.CommonClientApiMapper;
import cool.sodo.common.core.service.CommonClientApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommonClientApiServiceImpl implements CommonClientApiService {

    @Resource
    private CommonClientApiMapper commonClientApiMapper;

    @Override
    public boolean validateClientApi(String clientId, String apiId) {

        return countClientApiByClientAndApi(clientId, apiId) != 0;
    }

    @Override
    public int countClientApiByClientAndApi(String clientId, String apiId) {

        LambdaQueryWrapper<ClientApi> clientApiLambdaQueryWrapper = Wrappers.lambdaQuery();
        clientApiLambdaQueryWrapper.eq(ClientApi::getClientId, clientId)
                .eq(ClientApi::getApiId, apiId);
        return commonClientApiMapper.selectCount(clientApiLambdaQueryWrapper);
    }
}
