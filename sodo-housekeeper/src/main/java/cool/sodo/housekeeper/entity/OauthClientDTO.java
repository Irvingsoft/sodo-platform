package cool.sodo.housekeeper.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OauthClientDTO implements Serializable {

    private String content;
    private Boolean inUse;
    private Boolean register;
    private Boolean captcha;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
