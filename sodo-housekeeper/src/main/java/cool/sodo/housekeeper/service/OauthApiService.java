package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.OauthApi;
import cool.sodo.housekeeper.entity.OauthApiRequest;

import java.util.List;

public interface OauthApiService {

    void insertOauthApi(OauthApi oauthApi, String userId);

    void deleteOauthApi(String id, String userId);

    void updateOauthApi(OauthApi oauthApi, String userId);

    void deleteOauthApi(List<String> apiIdList, String userId);

    void updateOauthApi(OauthApi oauthApi);

    void updateOauthApiRequestNum(OauthApi oauthApi);

    /**
     * 根据 ApiId 更新 OauthApi 访问数据
     *
     * @param apiId OauthApi.apiId
     */
    void updateOauthApiAccessByAsync(String apiId);

    OauthApi getOauthApi(String id);

    OauthApi getOauthApiIdentity(String id);

    OauthApi getOauthApiIdentityNullable(String id);

    OauthApi getOauthApiIdentityNullable(String path, String method);

    OauthApi getOauthApiInfoDetail(String apiId);

    IPage<OauthApi> pageOauthApiInfo(OauthApiRequest oauthApiRequest);

    List<OauthApi> listOauthApiBaseUse();

    List<OauthApi> listOauthApiBaseUse(String clientId);

    List<OauthApi> listOauthApiInfo();

    Boolean validateOauthApiOfInsert(OauthApi oauthApi);

    Boolean validateOauthApiOfUpdate(OauthApi oauthApi);
}
