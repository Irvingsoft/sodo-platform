package cool.sodo.catkin.base.service;

import cool.sodo.catkin.base.entity.SegmentId;

/**
 * @author TimeChaser
 * @date 2021/9/12 15:22
 */
public interface SegmentIdService {

    /**
     * 根据 bizType 获取下一个 SegmentId 对象
     *
     * @param bizType bizType
     * @return SegmentId
     */
    SegmentId getNextSegmentId(String bizType);
}
