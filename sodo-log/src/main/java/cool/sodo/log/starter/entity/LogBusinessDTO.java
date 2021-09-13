package cool.sodo.log.starter.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogBusinessDTO implements Serializable {

    private String serviceId;
    private String clientId;
    private String userId;
    private String requestId;
    private String requestMethod;
    private String businessId;
    private String businessType;
    private String content;
    private Date createBegin;
    private Date createEnd;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
