package cool.sodo.catkin.server;

import cool.sodo.catkin.server.service.SegmentIdService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CatkinApplicationTest {

    @Resource
    private SegmentIdService segmentIdService;

    @Test
    public void testService() {
        segmentIdService.getNextSegmentId("sss");
    }
}
