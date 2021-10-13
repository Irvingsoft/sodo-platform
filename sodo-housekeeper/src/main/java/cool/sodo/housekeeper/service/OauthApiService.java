package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.core.domain.OauthApi;
import cool.sodo.housekeeper.entity.OauthApiDTO;

import java.util.List;

public interface OauthApiService {

    void insertOauthApi(OauthApi oauthApi, String userId);

    void deleteOauthApi(String id, String userId);

    void updateOauthApi(OauthApi oauthApi, String userId);

    void deleteOauthApi(List<String> apiIdList, String userId);

    void updateOauthApi(OauthApi oauthApi);

    /**
     * 定时任务重置 OauthApi 每日访问数据
     *
     * @param oauthApi 更新实体
     */
    void updateOauthApiAccessDailyByScheduleAsync(OauthApi oauthApi);

    /**
     * 定时任务重置 OauthApi 每周访问数据
     *
     * @param oauthApi 更新实体
     */
    void updateOauthApiAccessWeeklyByScheduleAsync(OauthApi oauthApi);

    /**
     * 定时任务重置 OauthApi 每月访问数据
     *
     * @param oauthApi 更新实体
     */
    void updateOauthApiAccessMonthlyByScheduleAsync(OauthApi oauthApi);

    /**
     * 消息队列根据 ApiId 更新 OauthApi 访问数据
     *
     * @param apiId OauthApi.apiId
     */
    void updateOauthApiAccessByMq(String apiId);

    OauthApi getOauthApi(String id);

    OauthApi getOauthApiIdentity(String id);

    OauthApi getOauthApiIdentityNullable(String id);

    OauthApi getOauthApiIdentityNullable(String path, String method);

    OauthApi getOauthApiInfoDetail(String apiId);

    IPage<OauthApi> pageOauthApiInfo(OauthApiDTO oauthApiDTO);

    List<OauthApi> listOauthApiBaseUse();

    List<OauthApi> listOauthApiBaseUse(String clientId);

    List<OauthApi> listOauthApiInfo();

    Boolean validateOauthApiOfInsert(OauthApi oauthApi);

    Boolean validateOauthApiOfUpdate(OauthApi oauthApi);
}
