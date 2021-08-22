package cool.sodo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.ResultEnum;
import cool.sodo.common.exception.SoDoException;
import cool.sodo.common.service.CommonOauthClientService;
import cool.sodo.common.util.HttpUtil;
import cool.sodo.common.util.WebUtil;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.DigestUtils;
import org.springframework.util.PatternMatchUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 参数签名验证、防止重复请求验证
 *
 * @author TimeChaser
 * @date 2021/5/30 12:33
 */
public class ApiSignatureFilter extends ZuulFilter {

    public static final String ERROR_SIGNATURE = "签名密钥已失效或者签名已被伪造！";
    public static final String ERROR_NONCE = "请勿重复请求！";
    public static final String ERROR_TIMESTAMP = "请求已过时效！";

    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private CommonOauthClientService oauthClientService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 此拦截器验证流程：
     * <br>
     * 1. 接口签名（防篡改请求数据（校验参数和业务参数））
     * <br>
     * 2. Nonce 唯一性验证（防重放）
     * <br>
     * 3. Timestamp 合法性（防重放）
     * <p>
     * Api 签名构思（Rsa 与客户端约定签名密钥）
     * <br>
     * 1. 客户端请求前先本地检查是否存在签名加密密钥，不存在，向服务端发出请求
     * <br>
     * 2. 服务端生成公钥和私钥，把 SessionId 作为 key 值，私钥作为 value 放入缓存，将公钥返回给客户端
     * <br>
     * 3. 客户端随机生成签名加密密钥，放入浏览器临时缓存（Session），用公钥加密后发送给后端
     * <br>
     * 4. 后端私钥解密获得密钥，把 SessionId 作为 key 值，密钥作为 value 放入缓存，设置过期时间 1 天（？或者更短 1 H？）
     * <br>
     * 5. 如果检查到客户端的请求签名错误，返回特定错误，前端请求拦截根据错误码判断是否需要重新确立签名加密密钥并重新发起请求
     * <p>
     * Vue 使用的缓存插件
     * https://www.npmjs.com/package/good-storage
     *
     * @author TimeChaser
     * @date 2021/6/10 23:40
     */
    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (isSkip(request)) {
            return null;
        }

        String clientId = WebUtil.getHeader(request, Constants.CLIENT_ID);
        String nonce = WebUtil.getHeader(request, Constants.NONCE);
        long timestamp = Long.parseLong(WebUtil.getHeader(request, Constants.TIMESTAMP));
        String body = HttpUtil.getBodyString(WebUtil.transformToContentCachingRequest(request));
        String signatureKey = (String) redisCacheHelper.get(Constants.SIGNATURE_KEY_CACHE_PREFIX + request.getHeader(Constants.SIGNATURE_KEY));
        String signature = WebUtil.getHeader(request, Constants.SIGNATURE);


        if (!redisCacheHelper.hasKey(signatureKey)) {
            currentContext.setResponseStatusCode(ResultEnum.INVALID_SIGNATURE.getCode());
            throw new SoDoException(ResultEnum.INVALID_SIGNATURE, ERROR_SIGNATURE);
        }
        if (System.currentTimeMillis() - timestamp > Constants.TIMESTAMP_EXPIRE) {
            currentContext.setResponseStatusCode(ResultEnum.BAD_REQUEST.getCode());
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_TIMESTAMP);
        }
        if (redisCacheHelper.hasKey(Constants.NONCE_CACHE_PREFIX + nonce)
                && redisCacheHelper.get(Constants.NONCE_CACHE_PREFIX + nonce).equals(signature)) {
            currentContext.setResponseStatusCode(ResultEnum.BAD_REQUEST.getCode());
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_NONCE);
        }
        if (!DigestUtils.md5DigestAsHex((clientId + nonce + timestamp + body + signatureKey).getBytes()).equals(signature)) {
            currentContext.setResponseStatusCode(ResultEnum.INVALID_SIGNATURE.getCode());
            throw new SoDoException(ResultEnum.INVALID_SIGNATURE, ERROR_SIGNATURE);
        }

        redisCacheHelper.set(Constants.NONCE_CACHE_PREFIX + nonce, signature, Constants.NONCE_CACHE_EXPIRE);
        return null;
    }

    private Boolean isSkip(HttpServletRequest request) {
        return PatternMatchUtils.simpleMatch(cool.sodo.zuul.common.Constants.SIGNATURE_IGNORE, request.getRequestURI())
                || oauthClientService.getOauthClientIdentity(WebUtil.getHeader(request, Constants.CLIENT_ID)).getSignature();
    }
}
