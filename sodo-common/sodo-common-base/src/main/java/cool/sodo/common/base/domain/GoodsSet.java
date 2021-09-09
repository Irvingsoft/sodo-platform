package cool.sodo.common.base.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import cool.sodo.common.base.util.StringUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "goods_set")
public class GoodsSet implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String setId;

    @NotNull(message = "商品 ID 不能为空")
    private String shopId;

    private String name;

    private Integer sort;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    @TableField(exist = false)
    private List<GoodsChoice> goodsChoiceList;

    public void update(GoodsSet goodsSet) {
        if (!StringUtil.isEmpty(goodsSet.name)) {
            this.name = goodsSet.name;
        }
        if (!StringUtil.isEmpty(goodsSet.sort)) {
            this.sort = goodsSet.sort;
        }
    }
}
