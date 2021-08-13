package cool.sodo.common.service;

import cool.sodo.common.domain.OauthApi;

/**
 * 通用 OauthApi Service
 *
 * @author TimeChaser
 * @date 2021/6/23 11:45
 */
public interface CommonOauthApiService {

    /**
     * 根据 ApiId 查询 OauthApi 基本信息
     *
     * @param apiId OauthApi.apiId
     * @return cool.sodo.common.domain.OauthApi
     */
    OauthApi getOauthApiBaseNullable(String apiId);

    /**
     * 根据 请求路径 和 请求方法 匹配查询 OauthApi 身份信息
     *
     * @param path   请求路径
     * @param method 请求方法
     * @return cool.sodo.common.domain.OauthApi
     */
    OauthApi getOauthApiIdentityByPathAndMethod(String path, String method);
}
