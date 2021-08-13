package cool.sodo.logger.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogBusinessRequest implements Serializable {

    private String serviceId;
    private String clientId;
    private String userId;
    private String requestMethod;
    private String businessId;
    private String businessType;
    private String content;
    private Date createBegin;
    private Date createEnd;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
