package cool.sodo.user;

import cool.sodo.common.base.component.RedisCacheHelper;
import cool.sodo.common.base.domain.Role;
import cool.sodo.common.base.domain.User;
import cool.sodo.common.base.service.CommonUserService;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.component.PasswordHelper;
import cool.sodo.redis.starter.annotation.Lock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class UserTestApplication {

    @Resource
    private PasswordHelper passwordHelper;
    @Resource
    private CommonUserService commonUserService;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    @Test
    public void generateAdmin() {

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        passwordHelper.encryptPassword(user);
        System.out.println(user);
    }

    @Test
    public void generate() {

        ArrayList<Role> roles = new ArrayList<>();
        if (roles.isEmpty()) {
            System.out.println("empty");
        } else {
            System.out.println("yes");
        }
    }

    @Test
    public void isEmpty() {

        ArrayList<Role> roles = new ArrayList<>();
        if (StringUtil.isEmpty(roles)) {
            System.out.println("empty");
        } else {
            System.out.println("yes");
        }
        roles = null;
        if (StringUtil.isEmpty(roles)) {
            System.out.println("empty");
        } else {
            System.out.println("yes");
        }
    }

    @Test
    public void testIfAbsent() {

        System.out.println(redisCacheHelper.setIfAbsent("aaa", "aa"));
        System.out.println(redisCacheHelper.setIfAbsent("aaa", "aa"));
    }

    @Test
    @Lock(key = "12345", leaseTime = 5L, timeUnit = TimeUnit.SECONDS)
    public void testLock() {

    }
}
