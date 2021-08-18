package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import cool.sodo.common.util.StringUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "shop")
public class Shop implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    @NotNull(message = "店铺 ID 不能为空")
    private String shopId;

    private String userId;

    private String schoolId;

    private String areaId;

    private Integer floor;

    private String stall;

    private String name;

    private String avatarUrl;

    private String backgroundUrl;

    private String notice;

    private Integer goodsNum;

    private String sign;

    private String phone;

    private BigDecimal leastPrice;

    private BigDecimal deliveryPrice;

    private Double score;

    private Integer ordersAll;

    private Integer ordersMonth;

    private Integer ordersWeek;

    private Integer ordersDay;

    private BigDecimal incomeAll;

    private BigDecimal incomeMonth;

    private BigDecimal incomeWeek;

    private BigDecimal incomeDay;

    private BigDecimal accountBalance;

    private Boolean predestine;

    private Boolean pick;

    private Boolean delivery;

    private Integer rank;

    private Boolean open;

    private Boolean valid;

    @TableField(exist = false)
    private Boolean inBusiness;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    @TableField(exist = false)
    private List<ShopCategory> categoryList;

    @TableField(exist = false)
    private List<Schedule> scheduleList;

    @TableField(exist = false)
    private List<ShopMenu> shopMenuList;

    public void handlePrivacy() {
        this.incomeAll = null;
        this.incomeMonth = null;
        this.incomeWeek = null;
        this.incomeDay = null;
        this.accountBalance = null;
    }

    public void update(Shop shop) {
        if (!StringUtil.isEmpty(shop.name)) {
            this.name = shop.name;
        }
        if (!StringUtil.isEmpty(shop.avatarUrl)) {
            this.avatarUrl = shop.avatarUrl;
        }
        if (!StringUtil.isEmpty(shop.backgroundUrl)) {
            this.backgroundUrl = shop.backgroundUrl;
        }
        if (!StringUtil.isEmpty(shop.notice)) {
            this.notice = shop.notice;
        }
        if (!StringUtil.isEmpty(shop.phone)) {
            this.phone = shop.phone;
        }
        if (!StringUtil.isEmpty(shop.leastPrice)) {
            this.leastPrice = shop.leastPrice;
        }
        if (!StringUtil.isEmpty(shop.predestine)) {
            this.predestine = shop.predestine;
        }
        if (!StringUtil.isEmpty(shop.pick)) {
            this.pick = shop.pick;
        }
        if (!StringUtil.isEmpty(shop.delivery)) {
            this.delivery = shop.delivery;
        }
    }
}
