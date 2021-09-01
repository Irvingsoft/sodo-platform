package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.housekeeper.entity.OauthClientDTO;

import java.util.List;

public interface OauthClientService {

    void insertOauthClient(OauthClient oauthClient, String userId);

    void updateOauthClient(OauthClient oauthClient, String userId);

    void updateOauthClient(OauthClient oauthClient);

    void deleteOauthClient(String clientId, String userId);

    OauthClient getOauthClient(String clientId);

    OauthClient getOauthClientIdentity(String clientId);

    OauthClient getOauthClientIdentityNullable(String clientId);

    OauthClient getOauthClientInfoDetail(String clientId);

    IPage<OauthClient> pageOauthClientInfo(OauthClientDTO oauthClientDTO);

    List<OauthClient> listOauthClientBaseUse();
}
