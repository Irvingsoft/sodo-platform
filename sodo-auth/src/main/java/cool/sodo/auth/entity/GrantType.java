package cool.sodo.auth.entity;

/**
 * 认证方式枚举
 *
 * @author TimeChaser
 * @date 2021/7/4 18:57
 */
public enum GrantType {

    UNRECOGNIZED(-1, "不支持的方式"),
    AUTHCODE(0, "认证码");

    private Integer type;
    private String desc;

    GrantType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
