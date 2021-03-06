package cool.sodo.auth.service;

import cool.sodo.auth.entity.*;

import javax.servlet.http.HttpServletRequest;

public interface OauthAuthService {

    AuthType getAuthType(AuthenticateRequest authenticateRequest);

    GrantType getGrantType(AuthorizeRequest authorizeRequest);

    void saveAuthCode(String authCode, AuthenticationIdentity authenticationIdentity);

    boolean validateCaptcha(String captcha, HttpServletRequest request);

    AuthenticationIdentity getAuthIdentityByAuthCode(String authCode);

    void removeAuthCode(String authCode);

    String generateCode();

    String loginUsernameAndPassword(AuthenticateRequest authenticateRequest, HttpServletRequest request);

    String loginWxApp(AuthenticateRequest authenticateRequest, HttpServletRequest request);

    AuthorizationIdentity authorize(AuthorizeRequest authorizeRequest, HttpServletRequest request);
}
