package cool.sodo.catkin.server.factory.impl;

import cool.sodo.catkin.base.factory.AbstractIdGeneratorFactory;
import cool.sodo.catkin.base.generator.IdGenerator;
import cool.sodo.catkin.base.generator.impl.CacheIdGenerator;
import cool.sodo.catkin.base.service.SegmentIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/12 21:28
 */
@Component
@Slf4j
public class IdGeneratorFactoryServer extends AbstractIdGeneratorFactory {

    @Resource
    private SegmentIdService segmentIdService;

    @Override
    protected IdGenerator createIdGenerator(String bizType) {
        log.info("createIdGenerator :{}", bizType);
        return new CacheIdGenerator(bizType, segmentIdService);
    }
}
