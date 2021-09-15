package cool.sodo.auth.controller;

import com.wf.captcha.SpecCaptcha;
import cool.sodo.auth.entity.AuthType;
import cool.sodo.auth.entity.AuthenticateRequest;
import cool.sodo.auth.service.AccessTokenService;
import cool.sodo.auth.service.OauthAuthService;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.IDContent;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.util.RsaUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.base.util.UUIDUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.component.RedisCacheHelper;
import cool.sodo.common.starter.component.PasswordHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

/**
 * 认证接口
 *
 * @author TimeChaser
 * @date 2021/7/12 23:52
 */
@RestController
@RequestMapping(value = "authenticate")
@Api(tags = "认证")
public class AuthenticationController {

    public static final String ERROR_PARAMS = "参数有误！";

    @Resource
    private OauthAuthService oauthAuthService;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private PasswordHelper passwordHelper;
    @Resource
    private AccessTokenService accessTokenService;

    @ApiOperation(value = "Authenticate Key 请求", notes = "账号密码登录获取密码加密密钥", produces = "application/json")
    @GetMapping(value = "key")
    public Result getPublicKey() {

        Map<String, String> rsaKeyMap = RsaUtil.initKey();
        String privateKey = rsaKeyMap.get(RsaUtil.PRIVATE_KEY);
        IDContent idContent = new IDContent(UUIDUtil.generate(), rsaKeyMap.get(RsaUtil.PUBLIC_KEY));
        redisCacheHelper.set(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + idContent.getId(),
                privateKey,
                Constants.RSA_PRIVATE_KEY_CACHE_EXPIRE_SECONDS);

        return Result.success(idContent);
    }

    @ApiOperation(value = "Authenticate captcha 图形验证码请求", notes = "账号密码登录获取图形验证码", produces = "application/json")
    @GetMapping(value = "captcha")
    public Result getCaptcha() {

        SpecCaptcha captcha = new SpecCaptcha(Constants.CAPTCHA_WIDTH, Constants.CAPTCHA_HEIGHT, Constants.CAPTCHA_LENGTH);
        IDContent idContent = new IDContent(UUIDUtil.generate(), captcha.toBase64());
        String captchaCode = captcha.text().toUpperCase(Locale.ROOT);
        redisCacheHelper.set(Constants.CAPTCHA_KEY_CACHE_PREFIX + idContent.getId(),
                captchaCode,
                Constants.CAPTCHA_CACHE_EXPIRE_SECONDS);

        return Result.success(idContent);
    }

    @ApiOperation(value = "Authenticate 请求", notes = "获取平台授权码，1、账号密码验证；2、微信授权码 + 用户加密信息 + 偏移量验证", produces = "application/json")
    @PostMapping(value = "")
    public Result authenticate(@RequestBody @Valid AuthenticateRequest authenticateRequest, HttpServletRequest request) {

        AuthType authType = oauthAuthService.getAuthType(authenticateRequest);

        if (AuthType.WECHATAPP.equals(authType)) {
            if (StringUtil.isEmpty(authenticateRequest.getCode())
                    || StringUtil.isEmpty(authenticateRequest.getEncryptedData())
                    || StringUtil.isEmpty(authenticateRequest.getIv())) {
                return Result.badRequest(ERROR_PARAMS);
            }

            return Result.success(oauthAuthService.loginWxApp(authenticateRequest, request));
        } else if (AuthType.BASIC.equals(authType)) {
            if (StringUtil.isEmpty(authenticateRequest.getUsername())
                    || StringUtil.isEmpty(authenticateRequest.getPassword())) {
                return Result.badRequest(ERROR_PARAMS);
            }

            authenticateRequest.setPassword(passwordHelper.decryptPassword(request, authenticateRequest.getPassword()));
            return Result.success(oauthAuthService.loginUsernameAndPassword(authenticateRequest, request));
        }

        return Result.of(ResultEnum.INVALID_AUTH);
    }

    @GetMapping(value = "logout")
    public Result logout(HttpServletRequest request) {

        accessTokenService.deleteCache(WebUtil.getAccessToken(request));
        return Result.success();
    }
}
