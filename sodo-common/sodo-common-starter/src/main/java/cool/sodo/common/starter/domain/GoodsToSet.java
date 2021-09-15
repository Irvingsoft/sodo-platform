package cool.sodo.common.starter.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "goods_to_set")
public class GoodsToSet implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String goodsId;

    private String setId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;
}
