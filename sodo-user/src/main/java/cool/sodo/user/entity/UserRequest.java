package cool.sodo.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户分页查询请求实体
 *
 * @author TimeChaser
 * @date 2021/7/3 15:11
 */
@Data
public class UserRequest implements Serializable {

    private String clientId;
    private Integer gender;
    private Integer status;
    private String identity;
    private String content;
    private String province;
    private String city;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
