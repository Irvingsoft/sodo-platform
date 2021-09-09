package cool.sodo.common.base.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "shop_category")
public class ShopCategory implements Serializable {

    @TableId
    private String categoryId;

    private String schoolId;

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer shopNum;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updateAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String createBy;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String updateBy;
}
