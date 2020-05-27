package org.linn.constants;

public class SecurityConstants {

    /**
     * 默认的处理验证码的url
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 当需要身份认证时，默认跳转的url
     */
    public static final String DEFAULT_UN_AUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认用户名密码登录处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/from";

    /**
     * 默认收集登录处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认登录页面
     */
    public static final String DEFAULT_LOGIN_PAGE_URL = "/login.html";

    /**
     * 注册页面
     */
    public static final String DEFAULT_REGISTER_PAGE_URL = "/register.html";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * session失效默认的跳转地址
     */
    public static final String DEFAULT_SESSION_INVALID_URL = "/session/invalid";
}
