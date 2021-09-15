package cool.sodo.common.starter.component;

import cool.sodo.common.base.entity.Constants;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.RsaUtil;
import cool.sodo.common.base.util.WebUtil;
import cool.sodo.common.core.component.RedisCacheHelper;
import cool.sodo.common.core.domain.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 密码加密组件
 *
 * @author TimeChaser
 * @date 2021/7/14 19:45
 */
@Component
public class PasswordHelper {

    public static final String ERROR_PRIVATE_KEY = "私钥已失效！";

    @Resource
    private RedisCacheHelper redisCacheHelper;

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * 加密该用户的密码
     *
     * @param user 用户名
     */
    public void encryptPassword(@NotNull User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword =
                new SimpleHash(
                        algorithmName,
                        user.getPassword(),
                        ByteSource.Util.bytes(user.getSalt()),
                        hashIterations)
                        .toHex();
        user.setPassword(newPassword);
    }

    /**
     * 根据用户名和盐值加密
     *
     * @param password 密码
     * @param salt     盐
     */
    public String encryptPassword(@NotBlank String password, @NotBlank String salt) {
        return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(salt), hashIterations)
                .toHex();
    }

    public String decryptPassword(HttpServletRequest request, String content) {

        String passwordKey = WebUtil.getHeader(request, Constants.PASSWORD_KEY);
        if (!redisCacheHelper.hasKey(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + passwordKey)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, ERROR_PRIVATE_KEY);
        }
        String privateKey = (String) redisCacheHelper.get(Constants.RSA_PRIVATE_KEY_CACHE_PREFIX + passwordKey);
        return RsaUtil.decryptByPrivateKey(content, privateKey);
    }

    public boolean validatePassword(User user, String password) {
        return user.getPassword().equals(encryptPassword(password, user.getSalt()));
    }
}
