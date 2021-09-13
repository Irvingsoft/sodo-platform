package cool.sodo.catkin.server.controller;

import cool.sodo.catkin.server.entity.SegmentId;
import cool.sodo.catkin.server.factory.impl.IdGeneratorFactoryServer;
import cool.sodo.catkin.server.generator.IdGenerator;
import cool.sodo.catkin.server.service.CatkinTokenService;
import cool.sodo.catkin.server.service.SegmentIdService;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TimeChaser
 * @date 2021/9/12 22:47
 */
@RestController
@RequestMapping(value = "id")
public class IdController {

    @Resource
    private IdGeneratorFactoryServer idGeneratorFactoryServer;
    @Resource
    private SegmentIdService segmentIdService;
    @Resource
    private CatkinTokenService catkinTokenService;
    @Value("100000")
    private Integer batchSizeMax;

    @RequestMapping(value = "")
    public Result id(String token, String bizType, Integer batchSize) {

        batchSize = checkBatchSize(batchSize);
        if (!catkinTokenService.validate(token, bizType)) {
            return Result.of(ResultEnum.UNAUTHORIZED);
        }
        IdGenerator idGenerator = idGeneratorFactoryServer.getIdGenerator(bizType);
        List<Long> ids = idGenerator.nextId(batchSize);
        return Result.success(ids);
    }

    @RequestMapping(value = "simple")
    public Long idSimple(String token, String bizType) {

        if (!catkinTokenService.validate(token, bizType)) {
            throw new SoDoException(ResultEnum.BAD_REQUEST, "Catkin 认证信息有误！");
        }
        IdGenerator idGenerator = idGeneratorFactoryServer.getIdGenerator(bizType);
        return idGenerator.nextId();
    }

    @RequestMapping(value = "segment")
    public Result segmentId(String token, String bizType) {

        if (!catkinTokenService.validate(token, bizType)) {
            return Result.of(ResultEnum.UNAUTHORIZED);
        }
        return Result.success(segmentIdService.getNextSegmentId(bizType));
    }

    @RequestMapping(value = "segment/simple")
    public String segmentIdSimple(String token, String bizType) {

        if (!catkinTokenService.validate(token, bizType)) {
            return "";
        }
        SegmentId segmentId = segmentIdService.getNextSegmentId(bizType);
        return segmentId.getCurrentId() + "," + segmentId.getLoadingId() + "," + segmentId.getMaxId()
                + "," + segmentId.getDelta() + "," + segmentId.getRemainder();
    }

    private Integer checkBatchSize(Integer batchSize) {
        if (StringUtil.isEmpty(batchSize)) {
            batchSize = 1;
        }
        if (batchSize > batchSizeMax) {
            batchSize = batchSizeMax;
        }
        return batchSize;
    }
}
