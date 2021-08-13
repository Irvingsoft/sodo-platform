package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

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
        if (!StringUtils.isEmpty(shop.name)) {
            this.name = shop.name;
        }
        if (!StringUtils.isEmpty(shop.avatarUrl)) {
            this.avatarUrl = shop.avatarUrl;
        }
        if (!StringUtils.isEmpty(shop.backgroundUrl)) {
            this.backgroundUrl = shop.backgroundUrl;
        }
        if (!StringUtils.isEmpty(shop.notice)) {
            this.notice = shop.notice;
        }
        if (!StringUtils.isEmpty(shop.phone)) {
            this.phone = shop.phone;
        }
        if (!StringUtils.isEmpty(shop.leastPrice)) {
            this.leastPrice = shop.leastPrice;
        }
        if (!StringUtils.isEmpty(shop.predestine)) {
            this.predestine = shop.predestine;
        }
        if (!StringUtils.isEmpty(shop.pick)) {
            this.pick = shop.pick;
        }
        if (!StringUtils.isEmpty(shop.delivery)) {
            this.delivery = shop.delivery;
        }
    }
}
