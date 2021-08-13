package cool.sodo.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 认证请求实体
 *
 * @author TimeChaser
 * @date 2021/7/13 10:23
 */
@Data
public class AuthenticateRequest implements Serializable {

    private String username;
    private String password;
    private String captcha;

    private String code;
    private String encryptedData;
    private String iv;

    private String authType;
    private String redirectUri;
}
