package cool.sodo.housekeeper.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cool.sodo.common.base.domain.OauthIp;
import cool.sodo.housekeeper.entity.OauthIpDTO;

import java.util.List;

public interface OauthIpService {

    void insertOauthIp(OauthIp oauthIp, String createBy);

    void deleteOauthIp(String ipId, String deleteBy);

    void deleteOauthIp(List<String> ipIdList, String deleteBy);

    void updateOauthIp(OauthIp oauthIp);

    void updateOauthIp(OauthIp oauthIp, String updateBy);

    void updateOauthIpValidNumByAsync(String ipId);

    OauthIp getOauthIp(String ipId);

    IPage<OauthIp> pageOauthIpInfo(OauthIpDTO oauthIpDTO);

    void check(String ipId, String ip, String clientId);
}
