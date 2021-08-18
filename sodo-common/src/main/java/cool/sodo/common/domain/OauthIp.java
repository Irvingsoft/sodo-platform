package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cool.sodo.common.util.StringUtil;
import lombok.Data;

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

    private String ip;

    private String description;

    private Integer validNum;

    private Boolean valid;

    private String createBy;

    private String updateBy;

    private Date createAt;

    private Date updateAt;

    public void update(OauthIp oauthIp) {

        if (!StringUtil.isEmpty(oauthIp.getClientId())) {
            this.clientId = oauthIp.getClientId();
        }
        if (!StringUtil.isEmpty(oauthIp.getIp())) {
            this.ip = oauthIp.getIp();
        }
        if (!StringUtil.isEmpty(oauthIp.getDescription())) {
            this.description = oauthIp.getDescription();
        }
        if (!StringUtil.isEmpty(oauthIp.getValid())) {
            this.valid = oauthIp.getValid();
        }
        if (!StringUtil.isEmpty(oauthIp.getUpdateBy())) {
            this.updateBy = oauthIp.getUpdateBy();
        }
        this.updateAt = new Date();
    }
}
