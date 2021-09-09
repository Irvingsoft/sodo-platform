package cool.sodo.common.base.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import cool.sodo.common.base.util.StringUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "schedule")
public class Schedule implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String scheduleId;

    @NotNull(message = "日程所属对象 ID 不能为空")
    private String objectId;

    private Integer openHour;

    private Integer openMinute;

    private Integer closeHour;

    private Integer closeMinute;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    public void handlePrivacy() {
        this.scheduleId = null;
        this.objectId = null;
    }

    public void update(Schedule schedule) {
        if (!StringUtil.isEmpty(schedule.openHour)) {
            this.openHour = schedule.openHour;
        }
        if (!StringUtil.isEmpty(schedule.openMinute)) {
            this.openMinute = schedule.openMinute;
        }
        if (!StringUtil.isEmpty(schedule.closeHour)) {
            this.closeHour = schedule.closeHour;
        }
        if (!StringUtil.isEmpty(schedule.closeMinute)) {
            this.closeMinute = schedule.closeMinute;
        }
    }
}
