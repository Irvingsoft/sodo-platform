package cool.sodo.catkin.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author TimeChaser
 * @date 2021/9/12 17:29
 */
@Data
@TableName(value = "catkin_token")
public class CatkinToken implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String token;

    private String bizType;

    private String remark;

    private Date createTime;

    private Date updateTime;
}
