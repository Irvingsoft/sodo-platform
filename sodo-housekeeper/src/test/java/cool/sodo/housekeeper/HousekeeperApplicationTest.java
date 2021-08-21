package cool.sodo.housekeeper;

import com.alibaba.fastjson.JSON;
import cool.sodo.common.domain.OauthApi;
import cool.sodo.common.util.StringUtil;
import cool.sodo.housekeeper.entity.OauthApiDTO;
import cool.sodo.housekeeper.service.OauthApiService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
public class HousekeeperApplicationTest {

    @Resource
    private OauthApiService oauthApiService;

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

        System.out.println(JSON.toJSON(oauthApiService.pageOauthApiInfo(new OauthApiDTO())));
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
}
