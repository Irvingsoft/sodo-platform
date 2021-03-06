package cool.sodo.common.starter.domain;

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
@TableName(value = "shop_menu")
public class ShopMenu implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String menuId;

    @NotNull(message = "商店 ID 不能为空")
    private String shopId;

    private String name;

    private Integer goodsNum;

    private Integer sort;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    @TableField(exist = false)
    private List<Goods> goodsList;

    public void update(ShopMenu shopMenu) {
        if (!StringUtil.isEmpty(shopMenu.name)) {
            this.name = shopMenu.name;
        }
        if (!StringUtil.isEmpty(shopMenu.sort)) {
            this.sort = shopMenu.sort;
        }
    }
}
