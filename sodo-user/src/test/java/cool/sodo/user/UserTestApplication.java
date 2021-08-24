package cool.sodo.user;

import cool.sodo.common.component.PasswordHelper;
import cool.sodo.common.component.RedisCacheHelper;
import cool.sodo.common.domain.Role;
import cool.sodo.common.domain.User;
import cool.sodo.common.service.CommonUserService;
import cool.sodo.common.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

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
    public void testCache() {

        System.out.println(commonUserService.getIdentityDetail("1"));
        System.out.println(commonUserService.getIdentityDetail("1"));
        System.out.println(commonUserService.getIdentityDetail("1"));
    }

    @Test
    public void testIfAbsent() {

        System.out.println(redisCacheHelper.setIfAbsent("aaa", "aa"));
        System.out.println(redisCacheHelper.setIfAbsent("aaa", "aa"));
    }
}
