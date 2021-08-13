package cool.sodo.goods.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopBase implements Serializable {

    private String shopId;

    private String name;

    private String avatarUrl;

    private String sign;

    private Double score;

    private Integer ordersMonth;

    private Integer ordersWeek;

    private Integer ordersDay;

    private Boolean predestine;

    private Boolean pick;

    private Boolean delivery;

    private Boolean open;
}
