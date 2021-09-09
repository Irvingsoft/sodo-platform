package cool.sodo.auth.controller;

import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.IDContent;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.util.RsaUtil;
import cool.sodo.common.base.util.UUIDUtil;
import cool.sodo.common.base.util.WebUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 签名密钥接口
 *
 * @author TimeChaser
 * @date 2021/6/10 23:54
 */
@RestController
@RequestMapping(value = "signature")
public class SignatureKeyController {

    public static final String ERROR_PRIVATE_KEY = "私钥已失效！";

    @Resource
    private RedisCacheHelper redisCacheHelper;

    @GetMapping(value = "key")
    public Result getPublicKey() {

        Map<String, String> rsaKeyMap = RsaUtil.initKey();
        IDContent idContent = new IDContent(UUIDUtil.generate(), rsaKeyMap.get(RsaUtil.PUBLIC_KEY));
        String privateKey = rsaKeyMap.get(RsaUtil.PRIVATE_KEY);
        redisCacheHelper.set(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + idContent.getId(),
                privateKey,
                Constants.RSA_PRIVATE_KEY_CACHE_EXPIRE_SECONDS);

        return Result.success(idContent);
    }

    @PostMapping(value = "key")
    public Result insertSignatureKey(@RequestBody IDContent idContent, HttpServletRequest request) {

        String rsaKey = WebUtil.getHeader(request, Constants.RSA_PRIVATE_KEY);
        if (!redisCacheHelper.hasKey(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + rsaKey)) {
            return Result.badRequest(ERROR_PRIVATE_KEY);
        }
        String privateKey = (String) redisCacheHelper.get(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + rsaKey);

        String signatureKey = RsaUtil.decryptByPrivateKey(idContent.getContent(), privateKey);
        redisCacheHelper.set(Constants.SIGNATURE_KEY_CACHE_PREFIX + rsaKey,
                signatureKey,
                Constants.SIGNATURE_KEY_CACHE_EXPIRE_SECONDS);

        return Result.success();
    }

    @GetMapping(value = "key/validate")
    public Result validateSignatureKey(HttpServletRequest request) {

        String rsaKey = WebUtil.getHeader(request, Constants.SIGNATURE_KEY);
        return redisCacheHelper.hasKey(Constants.SIGNATURE_KEY_CACHE_PREFIX + rsaKey) ?
                Result.success(true) : Result.success(false);
    }
}
