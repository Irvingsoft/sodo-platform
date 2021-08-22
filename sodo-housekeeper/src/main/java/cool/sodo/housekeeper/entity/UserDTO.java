package cool.sodo.housekeeper.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    @NotBlank(message = "客戶端信息不能为空！")
    private String clientId;
    private String identity;
    private Integer status;
    private String content;

    private Integer pageNum;
    private Integer pageSize;
}
