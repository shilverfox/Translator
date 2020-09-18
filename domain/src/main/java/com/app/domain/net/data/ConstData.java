package com.app.domain.net.data;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class ConstData {
    /** The key that generated by Google Translate API */
    public final static String GOOGLE_APP_KEY = "";

    /** Base url */
    public final static String GOOGLE_TRANS_URL = "https://www.googleapis.com/language/translate/v2?";

    /** Url for patch of hot-fix */
    public final static String HOT_PATCH_URL = "";

    public final static String KEY_FUNCTION_ID = "functionId";

    public final static String FUNCTION_ID_TEST = "address/getAddressList";

    public final static String HOST = "http://vod.china1901.com:9000";

    public final static String VIDEO_HOST = "http://vod.china1901.com:9000";

    public final static String DEVICE_ID = "aaaa-aaaa-111";

    public final static String ORG_CODE = "00000001";

    /** 登录模块获取短信验证码接口，注册 */
    public final static int REQUEST_SMS_TYPE_REGISTER = 1;

    /** 登录模块获取短信验证码接口，修改密码 */
    public final static int REQUEST_SMS_TYPE_MODIFY_PASS = 2;

    /** 登录模块获取短信验证码token */
    public final static String LOGIN_SMS_TOKEN = "XQBYdcHKSO+RS16jQhf+cRYJqaerqCole";

    /** 注册用户类型 */
    public final static String REGISTER_USER_TYPE = "5";

    /** 分页，每页条数 */
    public final static int DEFAULT_PAGE_SIZE = 20;

    /** 无效的名家id */
    public final static int INVALID_CELEBRITY_ID = -1;

    /** 观看历史 */
    public final static String FUNCTION_ID_MY_HISTORY = "/Special/Special/getUserHistory";

    /** 我的收藏 */
    public final static String FUNCTION_ID_MY_FAVORITE = "/Special/Special/getUserCollect";

    /** 消息类型：评论 */
    public final static String MESSAGE_TYPE_COMMENT = "2";

    /** 消息类型：点赞 */
    public final static String MESSAGE_TYPE_NB = "3";

    /** 评论类型：我对视频的评论 */
    public final static String COMMENT_TYPE_TO_VIDEO = "1";

    /** 评论类型：我对用户的评论 */
    public final static String COMMENT_TYPE_TO_OTHER_USER = "3";

    /** 评论类型：我的所有赞 */
    public final static String COMMENT_TYPE_TO_ALL_NB_STUFF = "2";

    /** 视频清晰度： 标清 */
    public final static String VIDEO_DEFINITION_TYPE_STAND = "2";
    public final static String VIDEO_DEFINITION_TYPE_NAME_STAND = "标清";


    /** 视频清晰度： 高清 */
    public final static String VIDEO_DEFINITION_TYPE_HIGH = "1";
    public final static String VIDEO_DEFINITION_TYPE_NAME_HIGH = "高清";

    /** 评论列表排列模式， 最新（时间） */
    public final static String COMMENT_ORDER_MODE_TIME = "1";
}
