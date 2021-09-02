package cool.sodo.housekeeper.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author TimeChaser
 * @date 2021/9/2 23:11
 */
@Data
public class OauthIpDTO implements Serializable {

    @NotBlank
    private String clientId;
    private String content;
    @NotBlank
    private Boolean valid;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
