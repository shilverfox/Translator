package com.jbsx.view.login.data;

/**
 * 常量
 */

public class LoginConstData {
    /** 跳转到h5 */
    public final static String INTENT_HTML_MODEL = "htmlModel";

    /** 账号认证需要传递的Intent Key */
    public final static String INTENT_OPEN_URL = "mOpenUrl";
    public final static String INTENT_RETURN_URL = "mReturnUrl";
    public final static String INTENT_COOKIE_NAME = "mCookieName";
    public final static String INTENT_REQUEST_SRC = "requestSrc";

    public final static String RETURN_URL_KEY_THIRD = "succcb";
    public final static String RETURN_URL_KEY_JD = "returnurl";//"succcb";
    public final static String RETURN_URL_VALUE = "openmyapp.care";

    /** 用户协议url */
    public final static String LINK_USER_AGREEMENT = "http://jbsx.china1896.com/jbsx/src/forapp/agment.html";

    /** 无效页面来源 */
    public final static int TYPE_HTML_REQUEST_INVALID = -1;

    /** 京东账号登录风控 */
    public final static int TYPE_HTML_REQUEST_JD_ACCOUNT_VERIFY = 1;

    /** 微信登录账号风控 */
    public final static int TYPE_HTML_REQUEST_WECHAT_ACCOUNT_VERIFY = 3;

    /** 找回密码 */
    public final static int TYPE_HTML_REQUEST_RESET_PASSWORD = 5;
}
