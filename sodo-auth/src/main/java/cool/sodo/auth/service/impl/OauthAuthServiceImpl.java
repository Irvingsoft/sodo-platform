package cool.sodo.auth.service.impl;

import cool.sodo.auth.entity.*;
import cool.sodo.auth.message.UserMqProducer;
import cool.sodo.auth.message.UserMqProperty;
import cool.sodo.auth.service.AccessTokenService;
import cool.sodo.auth.service.OauthAuthService;
import cool.sodo.auth.service.OauthUserService;
import cool.sodo.auth.service.WechatAuthService;
import cool.sodo.common.core.component.RedisCacheHelper;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.*;
import cool.sodo.common.core.domain.AccessToken;
import cool.sodo.common.core.domain.OauthClient;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.core.service.CommonOauthClientService;
import cool.sodo.common.core.service.CommonUserService;
import cool.sodo.common.starter.component.PasswordHelper;
import cool.sodo.common.starter.domain.OauthUser;
import cool.sodo.rabbitmq.starter.entity.Notification;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 登录处理
 *
 * @author TimeChaser
 * @date 2021/7/4 18:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OauthAuthServiceImpl implements OauthAuthService {

    public static final String ERROR_INSERT_USER = "新增 OauthUser 失败，OpenID：";
    public static final String ERROR_PASSWORD = "密码错误！";
    public static final String ERROR_CAPTCHA = "图形验证码错误！";
    public static final String ERROR_USER_CLIENT = "用户所属客户端与登录客户端不一致！";
    public static final String ERROR_AUTH_CLIENT = "认证客户端与授权客户端不一致！";
    public static final String ERROR_AUTH_CODE = "认证码已失效！";
    public static final String ERROR_WECHAT_TOKEN = "未获取到 Wechat 令牌！";
    public static final String ERROR_OAUTH_USER = "用户信息解密失败！";

    @Resource
    private CommonOauthClientService oauthClientService;
    @Resource
    private WechatAuthService wechatAuthService;
    @Resource
    private OauthUserService oauthUserService;
    @Resource
    private CommonUserService userService;
    @Resource
    private AccessTokenService accessTokenService;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private PasswordHelper passwordHelper;
    @Resource
    private UserMqProperty userMqProperty;
    @Resource
    private UserMqProducer userMqProducer;

    /**
     * 获取登录认证方式
     *
     * @param authenticateRequest 认证请求实体
     * @return cool.sodo.auth.entity.AuthType
     */
    @Override
    public AuthType getAuthType(AuthenticateRequest authenticateRequest) {

        List<String> authTypes = Arrays.stream(AuthType.values()).map(AuthType::name).collect(Collectors.toList());

        if (StringUtil.isEmpty(authenticateRequest.getAuthType()) || !authTypes.contains(authenticateRequest.getAuthType())) {
            return AuthType.UNRECOGNIZED;
        }
        return AuthType.valueOf(authenticateRequest.getAuthType().toUpperCase());
    }

    @Override
    public GrantType getGrantType(AuthorizeRequest authorizeRequest) {

        List<String> grantTypes = Arrays.stream(GrantType.values()).map(GrantType::name).collect(Collectors.toList());

        if (StringUtil.isEmpty(authorizeRequest.getGrantType()) && !grantTypes.contains(authorizeRequest.getGrantType())) {
            return GrantType.UNRECOGNIZED;
        }
        return GrantType.valueOf(authorizeRequest.getGrantType().toUpperCase());
    }

    @Override
    public void saveAuthCode(String authCode, AuthenticationIdentity authenticationIdentity) {
        redisCacheHelper.set(Constants.AUTH_CODE_CACHE_PREFIX + authCode, authenticationIdentity, Constants.AUTH_CODE_CACHE_EXPIRE_SECONDS);
    }

    @Override
    public boolean validateCaptcha(String captcha, HttpServletRequest request) {
        String captchaCache = (String) redisCacheHelper.get(Constants.CAPTCHA_KEY_CACHE_PREFIX + WebUtil.getHeader(request, Constants.CAPTCHA_KEY));
        return !StringUtil.isEmpty(captcha) &&
                captcha.toUpperCase(Locale.ROOT).equals(captchaCache.toUpperCase(Locale.ROOT));
    }

    @Override
    public AuthenticationIdentity getAuthIdentityByAuthCode(String authCode) {
        AuthenticationIdentity authenticationIdentity = (AuthenticationIdentity) redisCacheHelper.get(Constants.AUTH_CODE_CACHE_PREFIX + authCode);
        if (StringUtil.isEmpty(authenticationIdentity)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_AUTH_CODE, authenticationIdentity);
        }
        return authenticationIdentity;
    }

    @Override
    public void removeAuthCode(String authCode) {
        redisCacheHelper.delete(Constants.AUTH_CODE_CACHE_PREFIX + authCode);
    }

    @Override
    public String generateCode() {
        return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 用户名密码登录
     * 1. 验证用户名是否存在
     * 2. 验证密码是否正确
     * 3. 验证用户所属客户端是否与登录客户端一致
     *
     * @param authenticateRequest 登录请求实体
     * @param request             请求实体
     * @return java.lang.String
     */
    @Override
    public String loginUsernameAndPassword(AuthenticateRequest authenticateRequest, HttpServletRequest request) {

        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        OauthClient oauthClient = oauthClientService.getOauthClientIdentity(clientId);
        if (oauthClient.getCaptcha() && !validateCaptcha(authenticateRequest.getCaptcha(), request)) {
            throw new SoDoException(ResultEnum.INVALID_CAPTCHA, ERROR_CAPTCHA, authenticateRequest);
        }

        User user = userService.getIdentity(authenticateRequest.getUsername(), clientId);
        userService.checkUserStatus(user);
        if (!user.getClientId().equals(clientId)) {
            throw new SoDoException(ResultEnum.INVALID_CLIENT, ERROR_USER_CLIENT, user);
        }
        if (!passwordHelper.validatePassword(user, authenticateRequest.getPassword())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_PASSWORD, authenticateRequest);
        }

        String authCode = generateCode();
        saveAuthCode(authCode, new AuthenticationIdentity(authenticateRequest.getUsername(), clientId, authenticateRequest.getRedirectUri()));
        return authCode;
    }

    /**
     * 微信小程序认证
     * 1. 获取客户端对应的 微信小程序密钥信息
     * 2. 获取微信小程序 Token
     * 3. 根据 Token 解密 前端得到的用户的加密信息
     * 4. 判断是否需要插入 OauthUser 记录，及发送异步消息
     * 5. 颁发并存储授权码（OpenId，ClientId）
     *
     * @param authenticateRequest 登录请求实体
     * @param request             请求实体
     * @return java.lang.String
     */
    @Override
    public String loginWxApp(AuthenticateRequest authenticateRequest, HttpServletRequest request) {

        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        OauthClient oauthClient = oauthClientService.getOauthClientIdentity(clientId);

        WechatToken wechatToken = wechatAuthService.getAccessToken(authenticateRequest.getCode(), oauthClient);
        if (wechatToken == null || StringUtil.isEmpty(wechatToken.getSessionKey())) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_WECHAT_TOKEN);
        }
        OauthUser oauthUser = JsonUtil.toObject(AesCbcUtil.decrypt(
                        authenticateRequest.getEncryptedData().replace(CharPool.SPACE, CharPool.PLUS)
                        , wechatToken.getSessionKey()
                        , authenticateRequest.getIv().replace(CharPool.SPACE, CharPool.PLUS)
                )
                , OauthUser.class);
        if (oauthUser == null) {
            throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_OAUTH_USER);
        }

        User user = userService.getIdentity(oauthUser.getOpenId(), clientId);
        if (StringUtil.isEmpty(user)) {
            // 微信小程序用户第一次登录
            oauthUser.setClientId(clientId);
            if (oauthUserService.insert(oauthUser) <= 0) {
                throw new SoDoException(ResultEnum.SERVER_ERROR, ERROR_INSERT_USER, oauthUser);
            }
            BeanUtils.copyProperties(oauthUser, user);
            user.setStatus(oauthClient.getUserStatus());

            userMqProducer.sendMessage(new Notification(
                    userMqProperty.getCreateType(),
                    cool.sodo.auth.common.Constants.SERVICE_ID,
                    user));
        } else {
            userService.checkUserStatus(user);
        }

        String authCode = generateCode();
        saveAuthCode(authCode, new AuthenticationIdentity(oauthUser.getOpenId(), clientId, authenticateRequest.getRedirectUri()));
        return authCode;
    }

    /**
     * 用 AuthCode 换取 AccessToken
     * 1. 检查授权码是否有效
     * 2. 校验认证客户端和授权客户端是否一致
     * 3. 检查数据库中是否存在该用户对应的 Token 信息
     * 4. 存在则检查过期时间，未过期则刷新 Token，已过期则删除原 Token，重新生成 Token
     * 5. 不存在则生成 Token，更新数据库信息
     * 6. 刷新缓存信息
     * 7. 删除 AuthCode 缓存
     *
     * @author TimeChaser
     * @date 2020/11/7 6:20 下午
     */
    @Override
    public AuthorizationIdentity authorize(AuthorizeRequest authorizeRequest, HttpServletRequest request) {

        // 检查 AuthCode 是否合法，并获取 AuthIdentity
        AuthenticationIdentity authenticationIdentity = getAuthIdentityByAuthCode(authorizeRequest.getCode());
        // 检查认证客户端、授权客户端是否相同
        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        if (!clientId.equals(authenticationIdentity.getClientId())) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_AUTH_CLIENT);
        }

        OauthClient oauthClient = oauthClientService.getOauthClientIdentity(clientId);
        AccessToken accessToken = getAccessToken(authenticationIdentity.getIdentity(), oauthClient);

        // Token 放入缓存
        redisCacheHelper.set(Constants.ACCESS_TOKEN_CACHE_PREFIX + accessToken.getToken(),
                accessToken,
                getExpire(oauthClient));
        // AuthCode 已换取 Token，失效
        removeAuthCode(authorizeRequest.getCode());

        HashMap<String, String> messageMap = new HashMap<>(Constants.HASHMAP_SIZE_DEFAULT);
        messageMap.put(Constants.USER_LOGIN_IDENTITY, accessToken.getIdentity());
        messageMap.put(Constants.USER_LOGIN_IP, WebUtil.getIp(request));
        userMqProducer.sendMessage(new Notification(
                userMqProperty.getLoginType(),
                cool.sodo.auth.common.Constants.SERVICE_ID,
                messageMap));
        return new AuthorizationIdentity(accessToken.getToken(),
                getRedirectUri(oauthClient, authenticationIdentity),
                accessToken.getExpireAt());
    }

    /**
     * 处理并发登录和共享 Token
     *
     * @param identity    身份标识
     * @param oauthClient 客户端
     * @return cool.sodo.common.starter.domain.AccessToken
     */
    private AccessToken getAccessToken(String identity, OauthClient oauthClient) {

        AccessToken accessToken;
        if (oauthClient.getConcurrentLogin()) {
            if (oauthClient.getShareToken()) {
                // 共享 Token 时，认为库中单个用户最多只有一条 Token 记录
                accessToken = accessTokenService.getByIdentity(identity);
                if (accessToken != null) {
                    // Token 存在，验证 Token
                    if (accessToken.getExpireAt().getTime() >= System.currentTimeMillis()) {
                        // 未过期，刷新 Token
                        accessToken.setExpireAt(getExpireAt(oauthClient));
                        accessTokenService.update(accessToken);
                    } else {
                        // 已过期，重新生成 Token
                        accessTokenService.deleteCache(accessToken.getToken());
                        accessToken = generatorAccessToken(identity,
                                oauthClient.getClientId(),
                                getExpireAt(oauthClient));
                    }
                } else {
                    // Token 不存在，生成 Token
                    accessToken = generatorAccessToken(identity,
                            oauthClient.getClientId(),
                            getExpireAt(oauthClient));
                }
            } else {
                accessToken = generatorAccessToken(identity, oauthClient.getClientId(), getExpireAt(oauthClient));
            }
        } else {
            accessTokenService.deleteCacheByIdentity(identity);
            accessToken = generatorAccessToken(identity, oauthClient.getClientId(), getExpireAt(oauthClient));
        }
        return accessToken;
    }

    /**
     * 重复操作提取的公共方法
     *
     * @author TimeChaser
     * @date 2020/11/7 8:07 下午
     */
    private AccessToken generatorAccessToken(String identity, String clientId, Date expireAt) {
        AccessToken accessToken;
        accessToken = new AccessToken();
        accessToken.setToken(generateCode());
        accessToken.setIdentity(identity);
        accessToken.setClientId(clientId);
        accessToken.setExpireAt(expireAt);
        accessTokenService.insert(accessToken);
        return accessToken;
    }

    private Date getExpireAt(OauthClient oauthClient) {
        return StringUtil.isEmpty(oauthClient.getTokenExpire()) ?
                new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_CACHE_EXPIRE_MILLISECONDS) :
                new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(oauthClient.getTokenExpire()));
    }

    private Long getExpire(OauthClient oauthClient) {
        return StringUtil.isEmpty(oauthClient.getTokenExpire()) ?
                Constants.ACCESS_TOKEN_CACHE_EXPIRE_SECONDS : oauthClient.getTokenExpire();
    }

    private String getRedirectUri(OauthClient oauthClient, AuthenticationIdentity authenticationIdentity) {
        return StringUtil.isEmpty(authenticationIdentity.getRedirectUri()) ?
                oauthClient.getRedirectUri() : authenticationIdentity.getRedirectUri();
    }
}
