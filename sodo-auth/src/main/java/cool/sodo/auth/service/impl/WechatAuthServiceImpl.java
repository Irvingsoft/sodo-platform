package cool.sodo.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.sodo.auth.entity.WechatToken;
import cool.sodo.auth.exception.WeChatException;
import cool.sodo.auth.service.WechatAuthService;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.JsonUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.domain.OauthClient;
import cool.sodo.common.starter.domain.OauthUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 由于微信小程序是由前端引导用户授权信息，信息在前端获取
 * 所以这里只用到了获取 AccessToken 的接口
 *
 * @author TimeChaser
 * @date 2020/11/6 1:12 上午
 */

@Service
public class WechatAuthServiceImpl implements WechatAuthService {

    public static final String ACCESS_TOKEN_OPENID_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    public static final String USER_INFO_URL =
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    public static final String ERROR_TOKEN = "获取 Token 失败！";
    public static final String ERROR_INFO = "获取信息失败！";
    public static final String ERROR_JSON = "异常 JSON 转换失败！";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public WechatToken getAccessToken(String code, OauthClient oauthClient) {

        String url = String.format(ACCESS_TOKEN_OPENID_URL, oauthClient.getClientId(), oauthClient.getClientSecret(), code);
        String objectString = restTemplate.getForObject(url, String.class);

        if (!StringUtil.isEmpty(objectString) && objectString.contains(Constants.OPEN_ID)) {
            return JsonUtil.toObject(objectString, WechatToken.class);
        } else {
            throw new WeChatException(ERROR_TOKEN, JsonUtil.toObject(objectString, WeChatException.WeChatError.class));
        }
    }

    @Override
    public OauthUser getUserInfo(String accessToken, String openId) {

        String url = String.format(USER_INFO_URL, accessToken, openId);
        String object = restTemplate.getForObject(url, String.class);

        if (!StringUtil.isEmpty(object) && object.contains(Constants.WX_ERROR_CODE)) {
            try {
                throw new WeChatException(ERROR_TOKEN, objectMapper.readValue(object, WeChatException.WeChatError.class));
            } catch (JsonProcessingException e) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_JSON);
            }
        }
        return JsonUtil.toObject(object, OauthUser.class);
    }
}
