package cool.sodo.zuul.service;

public interface AccessTokenService {

    boolean validateAccessToken(String token, String clientId);

}
