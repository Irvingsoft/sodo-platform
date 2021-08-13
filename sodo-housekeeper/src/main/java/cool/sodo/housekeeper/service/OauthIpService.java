package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.domain.OauthIp;
import cool.sodo.housekeeper.entity.OauthIpRequest;

public interface OauthIpService {

    void insertOauthIp(OauthIp oauthIp);

    void deleteOauthIp(String id);

    void updateOauthIp(OauthIp oauthIp);

    OauthIp getOauthIp(String id);

    OauthIp getOauthIpIdentity(String id);

    OauthIp getOauthIpIdentityNullable(String id);

    IPage<OauthIp> pageOauthIpInfo(OauthIpRequest oauthIpRequest);
}
