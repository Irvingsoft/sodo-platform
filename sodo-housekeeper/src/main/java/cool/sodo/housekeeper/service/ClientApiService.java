package cool.sodo.housekeeper.service;

import cool.sodo.common.domain.ClientApi;

import java.util.List;

public interface ClientApiService {

    void insertClientApi(ClientApi clientApi);

    void insertClientApi(List<ClientApi> clientApiList);

    void insertClientApiOfOauthApi(String apiId, List<String> clientIdList, String userId);

    void insertClientApiOfOauthClient(String clientId, List<String> apiIdList, String userId);

    void deleteClientApiByOauthApi(String apiId);

    void deleteClientApiByOauthApi(List<String> apiIdList);

    void deleteClientApiByOauthClient(String clientId);

    void deleteClientApiByOauthClientAndNotInOauthApi(String clientId, List<String> apiIdList);

    void deleteClientApiByOauthApiAndNotInOauthClient(String apiId, List<String> clientIdList);

    void updateClientApiOfOauthClient(String clientId, List<String> apiIdList, String userId);

    void updateClientApiOfOauthApi(String apiId, List<String> clientIdList, String userId);

    ClientApi getClientApiIdentity(String id);

    ClientApi getClientApiIdentityNullable(String id);

    List<String> listClientApiApiId(String clientId);

    List<String> listClientApiClientId(String apiId);
}
