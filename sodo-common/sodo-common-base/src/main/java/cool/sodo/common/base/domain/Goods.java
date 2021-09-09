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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "goods")
public class Goods implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String goodsId;

    @NotNull(message = "商店 ID 不能为空！")
    private String shopId;

    @NotNull(message = "商店菜单 ID 不能为空！")
    private String menuId;

    private String name;

    private String avatarUrl;

    private Integer weight;

    private String description;

    private String priceDescription;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private BigDecimal packingPrice;

    private Integer discountLimit;

    private Boolean discount;

    private Double score;

    private Integer ordersAll;

    private Integer ordersMonth;

    private Integer ordersWeek;

    private Integer ordersDay;

    private BigDecimal incomeAll;

    private BigDecimal incomeMonth;

    private BigDecimal incomeWeek;

    private BigDecimal incomeDay;

    private Integer stockNum;

    private Boolean stock;

    private Integer sort;

    private Boolean sell;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    @TableField(exist = false)
    private List<GoodsSet> goodsSetList;

    public void update(Goods goods) {
        if (!StringUtil.isEmpty(goods.menuId)) {
            this.menuId = goods.menuId;
        }
        if (!StringUtil.isEmpty(goods.name)) {
            this.name = goods.name;
        }
        if (!StringUtil.isEmpty(goods.avatarUrl)) {
            this.avatarUrl = goods.avatarUrl;
        }
        if (!StringUtil.isEmpty(goods.originalPrice)) {
            this.originalPrice = goods.originalPrice;
        }
        if (!StringUtil.isEmpty(goods.discountPrice)) {
            this.discountPrice = goods.discountPrice;
        }
        if (!StringUtil.isEmpty(goods.discount)) {
            this.discount = goods.discount;
        }
        if (!StringUtil.isEmpty(goods.stockNum)) {
            this.stockNum = goods.stockNum;
        }
        if (!StringUtil.isEmpty(goods.stock)) {
            this.stock = goods.stock;
        }
        if (!StringUtil.isEmpty(goods.sort)) {
            this.sort = goods.sort;
        }
    }

    public void handlePrivacy() {
        if (!this.discount) {
            this.discount = null;
            this.discountPrice = null;
            this.discountLimit = null;
        }
    }
}
