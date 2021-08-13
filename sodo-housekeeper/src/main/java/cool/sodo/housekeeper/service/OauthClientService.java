package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.OauthClient;
import cool.sodo.housekeeper.entity.OauthClientRequest;

import java.util.List;

public interface OauthClientService {

    void insertOauthClient(OauthClient oauthClient, String userId);

    void updateOauthClient(OauthClient oauthClient, String userId);

    void updateOauthClient(OauthClient oauthClient);

    void deleteOauthClient(String id, String userId);

    OauthClient getOauthClient(String id);

    OauthClient getOauthClientIdentity(String id);

    OauthClient getOauthClientIdentityNullable(String id);

    OauthClient getOauthClientInfoDetail(String clientId);

    IPage<OauthClient> pageOauthClientInfo(OauthClientRequest oauthClientRequest);

    List<OauthClient> listOauthClientBaseUse();
}
