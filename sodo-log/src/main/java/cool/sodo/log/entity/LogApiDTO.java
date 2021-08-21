package cool.sodo.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogApiDTO implements Serializable {

    private String apiId;
    private String serviceId;
    private String clientId;
    private String userId;
    private String requestId;
    private String requestMethod;
    private Integer responseStatus;
    private Integer timeBegin;
    private Integer timeEnd;
    private String content;
    private Date createBegin;
    private Date createEnd;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
