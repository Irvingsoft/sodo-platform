package cool.sodo.catkin.starter.generator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import cool.sodo.catkin.starter.feign.CatkinClientService;
import cool.sodo.catkin.starter.property.CatkinClientProperty;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/13 17:07
 */
public class MybatisKeyGenerator implements IdentifierGenerator {

    @Resource
    private CatkinClientService catkinClientService;
    @Resource
    private CatkinClientProperty catkinClientProperty;

    @Override
    public Number nextId(Object entity) {
        return catkinClientService.idSimple(catkinClientProperty.getCatkinToken(), catkinClientProperty.getCatkinBizType());
    }
}
