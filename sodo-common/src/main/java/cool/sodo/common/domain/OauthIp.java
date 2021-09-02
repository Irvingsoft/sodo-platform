package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.util.StringUtil;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * IP 信息，白名单/黑名单
 *
 * @author TimeChaser
 * @date 2021/6/9 20:42
 */
@Data
@TableName(value = "oauth_ip")
public class OauthIp implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String ipId;

    private String clientId;

    @Pattern(regexp = Constants.IP_REGEXP)
    private String ip;

    private String description;

    private Integer validNum;

    private Boolean valid;

    private String createBy;

    private String updateBy;

    private Date createAt;

    private Date updateAt;

    @TableLogic
    private Boolean deleted;

    public void init(String createBy) {

        this.ipId = null;
        this.validNum = 0;
        this.createBy = createBy;
    }

    public void update(OauthIp oauthIp, String updateBy) {

        if (!StringUtil.isEmpty(oauthIp.getClientId())) {
            this.clientId = oauthIp.getClientId();
        }
        if (!StringUtil.isEmpty(oauthIp.getIp())) {
            this.ip = oauthIp.getIp();
        }
        if (!StringUtil.isEmpty(oauthIp.getDescription())) {
            this.description = oauthIp.getDescription();
        }
        this.updateBy = updateBy;
        this.updateAt = new Date();
    }

    public void delete(String deleteBy) {

        this.updateBy = deleteBy;
        this.updateAt = new Date();
    }
}
