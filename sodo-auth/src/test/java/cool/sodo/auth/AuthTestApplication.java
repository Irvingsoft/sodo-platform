package cool.sodo.auth;

import com.alibaba.fastjson.JSONObject;
import cool.sodo.auth.message.UserMqProperty;
import cool.sodo.auth.service.AccessTokenService;
import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.domain.AccessToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class AuthTestApplication {

    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private UserMqProperty userMqProperty;
    @Resource
    private AccessTokenService accessTokenService;

    @Test
    public void test() {
        AccessToken accessToken = accessTokenService.getByIdentity("1");

        redisCacheHelper.set("ACCESS_TOKEN::1", accessToken, 60L);
        System.out.println(redisCacheHelper.get("ACCESS_TOKEN::1"));
    }

    @Test
    public void testUserMqProperty() {
        System.out.println(JSONObject.toJSON(userMqProperty));
    }

    @Test
    public void testCache() {

        accessTokenService.deleteCache("76ee966b0a483dd65d32a2acdd393cea");
    }
}
