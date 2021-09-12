package cool.sodo.catkin.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.catkin.base.entity.SegmentId;
import cool.sodo.catkin.server.common.Constants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TimeChaser
 * @date 2021/9/12 17:29
 */
@Data
@TableName(value = "catkin_info")
public class CatkinInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String bizType;

    private Long beginId;

    private Long maxId;

    private Integer step;

    private Integer delta;

    private Integer remainder;

    private Date createTime;

    private Date updateTime;

    private Long version;

    public SegmentId toSegmentId() {

        SegmentId segmentId = new SegmentId();
        segmentId.setCurrentId(new AtomicLong(this.getMaxId() - this.getStep()));
        segmentId.setMaxId(this.getMaxId());
        segmentId.setRemainder(this.getRemainder() == null ? 0 : this.getRemainder());
        segmentId.setDelta(this.getDelta() == null ? 1 : this.getDelta());
        segmentId.setLoadingId(segmentId.getCurrentId().get() + this.getStep() * Constants.LOADING_PERCENT / 100);
        return segmentId;
    }
}
