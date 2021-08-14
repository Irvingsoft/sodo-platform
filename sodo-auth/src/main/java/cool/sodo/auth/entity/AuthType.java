package cool.sodo.auth.entity;

/**
 * 认证类型枚举
 *
 * @author TimeChaser
 * @date 2021/6/15 15:26
 */
@SuppressWarnings("all")
public enum AuthType {

    /**
     * 认证类型枚举值
     *
     * @author TimeChaser
     * @date 2021/6/15 15:26
     */
    UNRECOGNIZED(-1, "不支持的方式"),
    BASIC(0, "普通方式，Oauth2"),
    WECHATAPP(1, "微信小程序认证方式"),
    WECHAT(2, "微信网站扫码认证方式");

    private Integer type;
    private String desc;

    AuthType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
