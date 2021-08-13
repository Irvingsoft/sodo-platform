package cool.sodo.common.entity;

/**
 * 统一返回状态码枚举
 *
 * @author TimeChaser
 * @date 2021/6/11 10:36
 */
public enum ResultEnum {

    /**
     * 枚举值
     *
     * @author TimeChaser
     * @date 2021/6/11 10:36
     */
    SUCCESS(200, "success"),
    BAD_REQUEST(400, "非法请求"),
    INVALID_CLIENT(400, "不存在的客户端"),
    INVALID_IP(400, "IP 不合法"),
    UNAUTHORIZED(401, "没有认证信息，请先登录"),
    INVALID_TOKEN(402, "令牌无效或已过期"),
    INVALID_AUTH(403, "不支持的认证方式"),
    INVALID_GRANT(403, "不支持的授权方式"),
    NOT_FOUND(404, "not_found"),
    INVALID_METHOD(405, "不支持的请求方法"),
    INVALID_SIGNATURE(406, "签名错误"),
    INVALID_CAPTCHA(407, "验证码错误"),
    IN_REVIEW(409, "账号审核中"),
    UNKNOWN_API(440, "Api 未录入"),
    UNUSED_API(441, "Api 未启用"),
    SERVER_ERROR(500, "服务器错误");

    private final Integer code;
    private final String description;

    ResultEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
