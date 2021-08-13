package cool.sodo.auth;

import com.alibaba.fastjson.JSONObject;
import cool.sodo.auth.message.UserMqProperty;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.AccessToken;
import cool.sodo.common.service.CommonAccessTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class AuthTestApplication {

    @Resource
    private CommonAccessTokenService accessTokenService;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private UserMqProperty userMqProperty;

    @Test
    public void test() {
        AccessToken accessToken = accessTokenService.getAccessTokenNullableByIdentity("1");

        redisCacheHelper.set("ACCESS_TOKEN::1", accessToken, 60L);
        System.out.println(redisCacheHelper.get("ACCESS_TOKEN::1"));
    }

    @Test
    public void testUserMqProperty() {
        System.out.println(JSONObject.toJSON(userMqProperty));
    }
}
