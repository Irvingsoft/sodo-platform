package cool.sodo.zuul.common;

public class Constants {

    public static final String CACHE_NAME = "sodo-zuul";

    public static final String[] SIGNATURE_IGNORE = {
            "/auth/authorize/key",
            "/auth/authorize/captcha",
            "/auth/signature/key",
            "/auth/signature/key/validate",
            "/**/v2/api-docs",
    };

    public static final String[] CLIENT_IGNORE = {
            "/**/v2/api-docs",
    };
}
