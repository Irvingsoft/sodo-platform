package cool.sodo.common.starter.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "shop_to_category")
public class ShopToCategory implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String shopId;

    private String categoryId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String createAt;
}
