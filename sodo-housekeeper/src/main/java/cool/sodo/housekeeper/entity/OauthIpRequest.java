package cool.sodo.housekeeper.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OauthIpRequest implements Serializable {

    private String content;
    private String clientId;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
