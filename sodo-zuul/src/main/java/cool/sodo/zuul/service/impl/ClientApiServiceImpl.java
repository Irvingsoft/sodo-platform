package cool.sodo.zuul.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.domain.ClientMenu;
import cool.sodo.zuul.mapper.ClientMenuMapper;
import cool.sodo.zuul.service.ClientApiService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientApiServiceImpl implements ClientApiService {

    @Resource
    private ClientMenuMapper clientMenuMapper;

    // TODO ClientMenu -> ClientApi
    @Override
    @Cacheable(cacheNames = "clientMenu", key = "#root.args[0]")
    public String[] listClientMenuPath(String clientId) {
        ArrayList<String> clientMenuPathList = new ArrayList<>();
        LambdaQueryWrapper<ClientMenu> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(ClientMenu::getClientId, clientId);
        List<ClientMenu> clientMenuList = clientMenuMapper.selectList(queryWrapper);

        clientMenuList.forEach(clientMenu -> {
            clientMenuPathList.add(clientMenu.getPath());
        });

        int size = clientMenuList.size();

        return size == 0 ? null : clientMenuPathList.toArray(new String[size]);
    }
}
