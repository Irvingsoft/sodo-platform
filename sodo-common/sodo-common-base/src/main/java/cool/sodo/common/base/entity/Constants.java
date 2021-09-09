package cool.sodo.common.base.entity;

@SuppressWarnings("all")
public class Constants {

    public static final String CACHE_NAME = "SODO";

    public static final String ERROR_LIMITS_AUTHORITY = "权限不足";

    /**
     * Redis Key Prefix
     *
     * @author TimeChaser
     * @date 2021/7/14 14:26
     */
    public static final String AUTH_CODE_CACHE_PREFIX = "AUTH_CODE::";
    public static final String ACCESS_TOKEN_CACHE_PREFIX = "ACCESS_TOKEN::";
    public static final String SIGNATURE_KEY_CACHE_PREFIX = "SIGNATURE_KEY::";
    public static final String NONCE_CACHE_PREFIX = "NONCE::";
    public static final String RSA_PRIVATE_KEY_CACHE_PREFIX = "PRIVATE_KEY::";
    public static final String REQUEST_LIMIT_CACHE_PREFIX = "REQUEST_LIMIT::";
    public static final String PASSWORD_KEY_CACHE_PREFIX = "PASSWORD_KEY::";
    public static final String CAPTCHA_KEY_CACHE_PREFIX = "CAPTCHA::";
    public static final String REQUEST_CACHE_PREFIX = "REQUEST::";
    public static final String USER_CHECK_LOCK_PREFIX = "USER_CHECK_LOCK::";

    /**
     * Redis key Name
     *
     * @author TimeChaser
     * @date 2021/7/17 15:14
     */
    public static final String ACCESS_TOKEN_CACHE_NAME = "ACCESS_TOKEN";
    public static final String OAUTH_CLIENT_CACHE_NAME = "CLIENT";
    public static final String USER_IDENTITY_CACHE_NAME = "USER_IDENTITY";
    public static final String USER_ROLE_CACHE_NAME = "USER_ROLE";

    /**
     * Redis Expire
     *
     * @author TimeChaser
     * @date 2021/7/17 15:14
     */
    public static final long AUTH_CODE_CACHE_EXPIRE_SECONDS = 2 * 60L; // 2 minutes
    public static final long ACCESS_TOKEN_CACHE_EXPIRE_SECONDS = 7 * 24 * 60 * 60L; // 1 week
    public static final long ACCESS_TOKEN_CACHE_EXPIRE_MILLISECONDS = 7 * 24 * 60 * 60 * 1000L; // 1 week
    public static final long NONCE_CACHE_EXPIRE_SECONDS = 60L;
    public static final long TIMESTAMP_EXPIRE_MILLISECONDS = 5 * 1000L;
    public static final long SIGNATURE_KEY_CACHE_EXPIRE_SECONDS = 12 * 60 * 60L;
    public static final long PASSWORD_KEY_CACHE_EXPIRE_SECONDS = 30 * 60L;
    public static final long RSA_PRIVATE_KEY_CACHE_EXPIRE_SECONDS = 30 * 60L;
    public static final long CAPTCHA_CACHE_EXPIRE_SECONDS = 30 * 60L;
    public static final long REQUEST_CACHE_EXPIRE_SECONDS = 60L;
    public static final long USER_CHECK_LOCK_EXPIRE_SECONDS = 1L;

    public static final long USER_CHECK_LOCK_TIME_OUT_MILLISECONDS = 500L;

    /**
     * Other Prefix
     *
     * @author TimeChaser
     * @date 2021/7/14 14:27
     */
    public static final String NICKNAME_PREFIX = ":)SODO-";

    /**
     * 请求头参数名
     *
     * @author TimeChaser
     * @date 2021/7/14 14:20
     */
    public static final String AUTHORIZATION = "Authorization";
    public static final String CLIENT_ID = "Client_ID";
    public static final String CLIENT_SECRET = "Client_Secret";
    public static final String TIMESTAMP = "Timestamp";
    public static final String NONCE = "Nonce";
    public static final String SIGNATURE_KEY = "Signature_Key";
    public static final String SIGNATURE = "Signature";
    public static final String USER_AGENT = "user-agent";
    public static final String RSA_PRIVATE_KEY = "Rsa_Private_Key";
    public static final String PASSWORD_KEY = "Password_Key";
    public static final String CAPTCHA_KEY = "Captcha_Key";
    public static final String REQUEST_ID = "Request_ID";

    /**
     * HashMap Keys
     *
     * @author TimeChaser
     */
    public static final String USER_LOGIN_IDENTITY = "identity";
    public static final String USER_LOGIN_IP = "ip";

    public static final String OPEN_ID = "openId";
    public static final String WX_ERROR_CODE = "errcode";

    public static final String ACCESS_EVENT = "access";
    public static final String CHECK_EVENT = "check";
    public static final String LOG_EVENT = "log";

    public static final int USER_TYPE_USER = 0;
    public static final int USER_TYPE_SHOP = 1;
    public static final int USER_TYPE_DELIVERY = 2;

    public static final int USER_STATUS_LOGOUT = -1;
    public static final int USER_STATUS_NORMAL = 0;
    public static final int USER_STATUS_REVIEW = 1;
    public static final int USER_STATUS_FREEZE = 2;

    public static final int CAPTCHA_WIDTH = 130;
    public static final int CAPTCHA_HEIGHT = 48;
    public static final int CAPTCHA_LENGTH = 5;

    public static final String SODO_AUTH = "auth";
    public static final String SODO_USER = "user";

    public static final String LOG_BUSINESS_INSERT = "插入";
    public static final String LOG_BUSINESS_DELETE = "删除";
    public static final String LOG_BUSINESS_UPDATE = "更新";

    public static final int HASHMAP_SIZE_DEFAULT = 8;

    public static final String BUSINESS_INSERT = "INSERT";
    public static final String BUSINESS_DELETE = "DELETE";
    public static final String BUSINESS_UPDATE = "UPDATE";

    /**
     * 各种正则表达式
     * <br>
     * 用户名正则      6-20 位，字母开头，字母、数字、-、_
     * <br>
     * 密码正则       至少 8 位，包括至少 1 个大写字母，1 个小写字母，1 个数字，1 个特殊字符
     *
     * @author TimeChaser
     * @date 2021/7/5 10:16
     */
    public static final String USERNAME_REGEXP = "/^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$/";
    public static final String PHONE_REGEXP = "/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$/";
    public static final String PASSWORD_REGEXP = "/^.*(?=.{8,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$/";
    public static final String EMAIL_REGEXP = "/^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$/";
    public static final String IDCARD_REGEXP = "/^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$/";
    public static final String IP_REGEXP = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";

    public static final String GATEWAY_PATH = "/zuul";
}
