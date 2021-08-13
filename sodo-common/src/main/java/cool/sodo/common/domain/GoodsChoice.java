package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "goods_choice")
public class GoodsChoice implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String choiceId;

    @NotNull(message = "商品 ID 不能为空")
    private String shopId;

    private String setId;

    private String name;

    private BigDecimal choicePrice;

    private Integer sort;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    public void update(GoodsChoice goodsChoice) {
        if (!StringUtils.isEmpty(goodsChoice.getName())) {
            this.name = goodsChoice.name;
        }
        if (!StringUtils.isEmpty(goodsChoice.getSort())) {
            this.sort = goodsChoice.sort;
        }
    }
}
