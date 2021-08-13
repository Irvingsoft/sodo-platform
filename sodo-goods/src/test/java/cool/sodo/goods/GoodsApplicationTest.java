package cool.sodo.goods;

import cool.sodo.goods.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class GoodsApplicationTest {

    @Resource
    private GoodsService goodsService;


    @Test
    public void test() {

        Calendar openAt = Calendar.getInstance();
        openAt.set(Calendar.HOUR, 11);
        openAt.set(Calendar.MINUTE, 23);

        Date date = new Date();

        System.out.println(date);
        System.out.println(openAt.getTime());
        System.out.println(date.before(openAt.getTime()));
    }

    @Test
    public void test1() {

    }

    @Test
    public void testGoodsDelete() {

    }
}

// TODO 商品折扣功能强化，折扣份数、折扣时间