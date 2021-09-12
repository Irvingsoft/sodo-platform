package cool.sodo.catkin.server.controller;

import cool.sodo.catkin.base.entity.SegmentId;
import cool.sodo.catkin.base.generator.IdGenerator;
import cool.sodo.catkin.base.service.SegmentIdService;
import cool.sodo.catkin.server.factory.impl.IdGeneratorFactoryServer;
import cool.sodo.catkin.server.service.CatkinTokenService;
import cool.sodo.common.base.entity.Result;
import cool.sodo.common.base.entity.ResultEnum;
import cool.sodo.common.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "")
    public Result id(String token, String bizType, Integer batchSize) {

        batchSize = checkBatchSize(batchSize);
        if (!catkinTokenService.validate(token, bizType)) {
            return Result.of(ResultEnum.UNAUTHORIZED);
        }
        IdGenerator idGenerator = idGeneratorFactoryServer.getIdGenerator(bizType);
        List<Long> ids = idGenerator.nextId(batchSize);
        return Result.success(ids);
    }

    @GetMapping(value = "simple")
    public String idSimple(String token, String bizType, Integer batchSize) {

        if (!catkinTokenService.validate(token, bizType)) {
            return "";
        }
        StringBuilder response = new StringBuilder();
        batchSize = checkBatchSize(batchSize);
        IdGenerator idGenerator = idGeneratorFactoryServer.getIdGenerator(bizType);
        if (batchSize == 1) {
            response.append(idGenerator.nextId());
        } else {
            for (Integer i = 0; i < batchSize; i++) {
                List<Long> ids = idGenerator.nextId(batchSize);
                for (Long id : ids) {
                    response.append(id).append(",");
                }
                response.deleteCharAt(response.length() - 1);
            }
        }
        return response.toString();
    }

    @GetMapping(value = "segment")
    public Result segmentId(String token, String bizType) {

        if (!catkinTokenService.validate(token, bizType)) {
            return Result.of(ResultEnum.UNAUTHORIZED);
        }
        return Result.success(segmentIdService.getNextSegmentId(bizType));
    }

    @GetMapping(value = "segment/simple")
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
