package cool.sodo.catkin.server.factory.impl;

import cool.sodo.catkin.server.factory.AbstractIdGeneratorFactory;
import cool.sodo.catkin.server.generator.IdGenerator;
import cool.sodo.catkin.server.generator.impl.CacheIdGenerator;
import cool.sodo.catkin.server.service.SegmentIdService;
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
