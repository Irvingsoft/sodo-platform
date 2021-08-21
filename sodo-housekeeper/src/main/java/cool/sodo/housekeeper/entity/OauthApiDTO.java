package cool.sodo.housekeeper.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * API 查询实体
 *
 * @author TimeChaser
 * @date 2021/5/31 21:15
 */
@Data
public class OauthApiDTO implements Serializable {

    private String clientId;
    private String content;
    private String service;
    private String method;
    private Boolean inUse;
    private Boolean auth;
    private Boolean requestLimit;
    private Boolean log;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
