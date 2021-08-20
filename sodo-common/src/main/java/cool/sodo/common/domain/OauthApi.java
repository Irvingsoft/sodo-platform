package cool.sodo.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import cool.sodo.common.util.StringUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 接口信息
 *
 * @author TimeChaser
 * @date 2021/5/30 12:34
 */
@Data
@TableName(value = "oauth_api")
public class OauthApi implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String apiId;

    private String name;

    private String code;

    @NotBlank(message = "OauthApi.path 字段不能为空！")
    private String path;

    private String service;

    @Pattern(regexp = "(^POST$|^PUT$|^PATCH$|^DELETE$|^GET$)", message = "OauthApi.method 值不在可选范围！")
    private String method;

    private String description;

    /**
     * 在时间段内的限制请求数量
     *
     * @date 2021/6/15 12:43
     */
    private Integer limitNum;

    /**
     * 接口限制时间段长度（秒）
     *
     * @date 2021/6/15 12:43
     */
    private Integer limitPeriod;

    private Integer requestDay;

    private Integer requestWeek;

    private Integer requestMonth;

    private Integer requestAll;

    /**
     * 是否开启接口请求数限制
     *
     * @date 2021/6/15 12:42
     */
    private Boolean requestLimit;

    /**
     * 是否开启日志
     *
     * @date 2021/6/15 12:42
     */
    private Boolean log;

    /**
     * 是否启用
     *
     * @date 2021/6/15 12:42
     */
    private Boolean inUse;

    /**
     * 是否需要身份认证
     *
     * @date 2021/6/15 12:42
     */
    private Boolean auth;

    private Date createAt;

    private Date updateAt;

    private String createBy;

    private String updateBy;

    @TableLogic
    private Boolean deleted;

    @TableField(exist = false)
    private User creator;
    @TableField(exist = false)
    private User updater;
    @TableField(exist = false)
    private List<String> clientIdList;

    public void init(String userId) {

        if (!StringUtil.isEmpty(this.limitNum) && this.limitNum < 0) {
            this.limitNum = 0;
        }
        if (!StringUtil.isEmpty(this.limitPeriod) && this.limitPeriod < 0) {
            this.limitPeriod = 0;
        }
        this.createBy = userId;
    }

    public void update(OauthApi oauthApi, String userId) {

        this.code = oauthApi.getCode();
        if (!StringUtil.isEmpty(oauthApi.getName())) {
            this.name = oauthApi.getName();
        }
        if (!StringUtil.isEmpty(oauthApi.getPath())) {
            this.path = oauthApi.getPath();
        }
        if (!StringUtil.isEmpty(oauthApi.getService())) {
            this.service = oauthApi.getService();
        }
        if (!StringUtil.isEmpty(oauthApi.getMethod())) {
            this.method = oauthApi.getMethod();
        }
        if (!StringUtil.isEmpty(oauthApi.getDescription())) {
            this.description = oauthApi.getDescription();
        }
        if (!StringUtil.isEmpty(oauthApi.getLimitNum())) {
            this.limitNum = oauthApi.getLimitNum();
        }
        if (!StringUtil.isEmpty(oauthApi.getLimitPeriod())) {
            this.limitPeriod = oauthApi.getLimitPeriod();
        }
        if (!StringUtil.isEmpty(oauthApi.getRequestLimit())) {
            this.requestLimit = oauthApi.getRequestLimit();
        }
        if (!StringUtil.isEmpty(oauthApi.getLog())) {
            this.log = oauthApi.getLog();
        }
        if (!StringUtil.isEmpty(oauthApi.getInUse())) {
            this.inUse = oauthApi.getInUse();
        }
        if (!StringUtil.isEmpty(oauthApi.getAuth())) {
            this.auth = oauthApi.getAuth();
        }
        this.updateBy = userId;
        this.updateAt = new Date();

        if (!StringUtil.isEmpty(this.limitNum) && this.limitNum < 0) {
            this.limitNum = 0;
        }
        if (!StringUtil.isEmpty(this.limitPeriod) && this.limitPeriod < 0) {
            this.limitPeriod = 0;
        }
    }

    public void delete(String userId) {

        this.updateBy = userId;
        this.updateAt = new Date();
    }
}
