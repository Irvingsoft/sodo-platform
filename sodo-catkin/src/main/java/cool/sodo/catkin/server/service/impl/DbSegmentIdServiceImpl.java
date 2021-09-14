package cool.sodo.catkin.server.service.impl;

import cool.sodo.catkin.server.common.Constants;
import cool.sodo.catkin.server.domain.CatkinInfo;
import cool.sodo.catkin.server.entity.SegmentId;
import cool.sodo.catkin.server.service.CatkinInfoService;
import cool.sodo.catkin.server.service.SegmentIdService;
import cool.sodo.common.base.exception.SoDoException;
import cool.sodo.common.base.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author TimeChaser
 * @date 2021/9/12 21:29
 */
@Service
@Slf4j
public class DbSegmentIdServiceImpl implements SegmentIdService {

    @Resource
    private CatkinInfoService catkinInfoService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public SegmentId getNextSegmentId(String bizType) {

        for (int i = 0; i < Constants.RETRY; i++) {

            CatkinInfo catkinInfo = catkinInfoService.getOneByBizType(bizType);
            if (StringUtil.isEmpty(catkinInfo)) {
                throw new SoDoException("can not find BizType:" + bizType);
            }
            Long newMaxId = catkinInfo.getMaxId() + catkinInfo.getStep();
            Long oldMaxId = catkinInfo.getMaxId();
            int row = catkinInfoService.updateMaxId(
                    catkinInfo.getId(),
                    newMaxId,
                    oldMaxId,
                    catkinInfo.getVersion(),
                    catkinInfo.getBizType());
            if (row == 1) {
                catkinInfo.setMaxId(newMaxId);
                SegmentId segmentId = catkinInfo.toSegmentId();
                log.info("getNextSegmentId success CatkinInfo:{} current:{}", catkinInfo, segmentId);
                return segmentId;
            } else {
                log.error("getNextSegmentId conflict CatkinInfo:{}", catkinInfo);
            }
        }
        throw new SoDoException("getNextSegmentId conflict");
    }
}
