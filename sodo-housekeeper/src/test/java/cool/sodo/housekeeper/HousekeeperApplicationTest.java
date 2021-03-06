package cool.sodo.housekeeper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cool.sodo.common.base.util.JsonUtil;
import cool.sodo.common.base.util.StringUtil;
import cool.sodo.common.core.component.RedisCacheHelper;
import cool.sodo.common.core.domain.OauthApi;
import cool.sodo.common.core.domain.User;
import cool.sodo.common.core.mapper.CommonUserMapper;
import cool.sodo.housekeeper.entity.OauthApiDTO;
import cool.sodo.housekeeper.schedule.OauthApiSchedule;
import cool.sodo.housekeeper.service.OauthApiService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class HousekeeperApplicationTest {

    @Resource
    private OauthApiService oauthApiService;
    @Resource
    private CommonUserMapper userMapper;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Resource
    private OauthApiSchedule oauthApiSchedule;

    /**
     * org.springframework.util.StringUtil 可以判断对象是否为空
     *
     * @author TimeChaser
     * @date 2021/5/31 22:51
     */
    @Test
    public void testSpringFrameworkStringUtil(Boolean aBoolean) {


        if (!StringUtil.isEmpty(aBoolean)) {
            System.out.println(aBoolean);
        } else {
            System.out.println("is null");
        }
    }

    @Test
    public void testSpringFrameworkStringUtil(OauthApi aBoolean) {


        if (!StringUtil.isEmpty(aBoolean)) {
            System.out.println(aBoolean);
        } else {
            System.out.println("is null");
        }
    }

    @Test
    public void test() {

        testSpringFrameworkStringUtil(oauthApiService.getOauthApiIdentityNullable("a"));
    }

    @Test
    public void testNullIdSelectMapper() {

    }

    @Test
    public void testPage() {

        System.out.println(JsonUtil.toJsonString(oauthApiService.pageOauthApiInfo(new OauthApiDTO())));
    }

    @Test
    public void testList() {

        ArrayList<String> nullList = null;
        ArrayList<String> emptyList = new ArrayList<>();
        System.out.println(StringUtil.isEmpty(nullList));
//        System.out.println(nullList.isEmpty());
        System.out.println(StringUtil.isEmpty(emptyList));
        System.out.println(emptyList.isEmpty());
    }

    @Test
    public void testFor() {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.eq(User::getUserId, "aaa");
        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);
        for (User user : userList) {
            user.setRoleIdList(new ArrayList<>());
        }
        System.out.println(userList);
    }

    @Test
    public void testListOauthApiInfo() throws InterruptedException {

        new Thread(() -> {
            oauthApiSchedule.resetRequestDay();
        }).start();
        new Thread(() -> {
            oauthApiSchedule.resetRequestWeek();
        }).start();
        new Thread(() -> {
            oauthApiSchedule.resetRequestMonth();
        }).start();
        Thread.sleep(1000);
        System.out.println(oauthApiService.listOauthApiInfo());
    }

}
