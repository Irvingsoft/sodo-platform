package cool.sodo.zuul.common;

public class Constants {

    public static final String CACHE_NAME = "sodo-zuul";

    public static final String[] SIGNATURE_IGNORE = {
            "/auth/authorize/key",
            "/auth/authorize/captcha",
            "/auth/signature/key",
            "/auth/signature/key/validate",
            "/zuul/v2/api-docs",
            "/housekeeper/v2/api-docs",
            "/auth/v2/api-docs",
            "/user/v2/api-docs",
            "/goods/v2/api-docs",
            "/order/v2/api-docs",
            "/payment/v2/api-docs",
    };

    public static final String[] CLIENT_IGNORE = {
            "/zuul/v2/api-docs",
            "/housekeeper/v2/api-docs",
            "/auth/v2/api-docs",
            "/user/v2/api-docs",
            "/goods/v2/api-docs",
            "/order/v2/api-docs",
            "/payment/v2/api-docs",
    };
}
