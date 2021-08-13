package cool.sodo.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 认证时，缓存中存放的认证信息
 *
 * @author TimeChaser
 * @date 2021/5/30 11:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationIdentity implements Serializable {

    private String identity;
    private String clientId;
    private String redirectUri;
}
