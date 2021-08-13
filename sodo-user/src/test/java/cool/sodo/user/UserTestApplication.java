package cool.sodo.user;

import cool.sodo.common.component.PasswordHelper;
import cool.sodo.common.domain.Role;
import cool.sodo.common.domain.User;
import cool.sodo.common.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
public class UserTestApplication {

    @Resource
    private PasswordHelper passwordHelper;

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
}
