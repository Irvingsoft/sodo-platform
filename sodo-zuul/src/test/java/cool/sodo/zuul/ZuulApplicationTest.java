package cool.sodo.zuul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class ZuulApplicationTest {

    @Resource
    private ServerProperties serverProperties;

    @Test
    public void testProperties() {

    }

    @Test
    public void testServerProperties() {

        System.out.println(serverProperties.getPort());
    }

    @Test
    public void testException() {
//        try {
//            throw new AsyncException("test");
//        } catch (Exception e) {
//            if (e instanceof AsyncException) {
////                e.printStackTrace();
//                System.out.println(Arrays.toString(e.getStackTrace()));
//            }
//        }
    }

    @Test
    public void testNullToString() {

        System.out.println("".toString());
        StringBuilder s = null;
        // NPE
        System.out.println(s.toString());
    }

    @Test
    public void testLogOutPut() {

//        LogApi logApi = new LogApi();
//        logApi.setMethodName("testLogOutPut");
//        log.info("ApiLog 抛出：" + logApi);
//
//        LogError logError = new LogError();
//        logError.setMethodName("testLogOutPut");
//        log.error("ErrorLog 抛出：" + logError);

    }
}
