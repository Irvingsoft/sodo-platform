package cool.sodo.goods.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopRequest implements Serializable {

    private String schoolId;
    private String categoryId;
    private Integer type;

    private Integer pageNum = 1;
    private Integer pageSize = 20;
}
