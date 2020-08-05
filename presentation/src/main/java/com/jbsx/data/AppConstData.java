package com.jbsx.data;

/**
 * 应用中的常量数据
 */
public class AppConstData {
    // 名家类型：10.导演,11.作者,12.解说,13.撰稿,14.主持人,15.主讲嘉宾,16.责任编辑,17.总编辑,18.学术、艺术顾问,19.总顾问,20.监制,21.总监制

    public final static int CELEBRITY_TYPE_DAOYAN = 10;
    public final static int CELEBRITY_TYPE_ZHUZHE = 11;
    public final static int CELEBRITY_TYPE_JIESHUO = 12;
    public final static int CELEBRITY_TYPE_ZHUANGAO = 13;
    public final static int CELEBRITY_TYPE_ZHUCHI = 14;
    public final static int CELEBRITY_TYPE_ZHUJIANG = 15;
    public final static int CELEBRITY_TYPE_ZRBJ = 16;
    public final static int CELEBRITY_TYPE_ZONGBJ = 17;
    public final static int CELEBRITY_TYPE_XSYSGW = 18;
    public final static int CELEBRITY_TYPE_ZONGGW = 19;
    public final static int CELEBRITY_TYPE_JIANZHI = 20;
    public final static int CELEBRITY_TYPE_ZONGJIANZHI = 21;

    /** 剧组角色列表，全部，用于播放页面的剧组简介显示 */
    public final static int[] CREW_ALL = {
            CELEBRITY_TYPE_DAOYAN,
            CELEBRITY_TYPE_ZHUCHI,
            CELEBRITY_TYPE_ZHUANGAO,
            CELEBRITY_TYPE_JIESHUO,
            CELEBRITY_TYPE_XSYSGW,
            CELEBRITY_TYPE_ZHUJIANG,
            CELEBRITY_TYPE_ZHUZHE,
            CELEBRITY_TYPE_ZRBJ,
            CELEBRITY_TYPE_ZONGBJ,
            CELEBRITY_TYPE_ZONGGW,
            CELEBRITY_TYPE_JIANZHI,
            CELEBRITY_TYPE_ZONGJIANZHI
    };

    /** 剧组角色列表，主要角色，用于播放页面的人员显示 */
    public final static int[] CREW_MAIN = {CELEBRITY_TYPE_DAOYAN, CELEBRITY_TYPE_ZHUANGAO, CELEBRITY_TYPE_ZHUCHI};


    /** 导航类型：首页 */
    public final static int TYPE_NAVI_MAIN = 0;

    /** 导航类型：专辑 */
    public final static int TYPE_NAVI_ALBUM = 1;

    /** 导航类型：视频 */
    public final static int TYPE_NAVI_VIDEO = 2;

    /** 导航类型：本地资源 */
    public final static int TYPE_NAVI_LOCAL = 3;


    /** 资源类型 专辑 */
    public final static int TYPE_RESOURCE_ALBUM = 1;

    /** 资源类型 视频 */
    public final static int TYPE_RESOURCE_VIDEO = 2;


    /** 发布状态 保存 */
    public final static int STATUS_PUBLISH_SAVE = 0;

    /** 发布状态 发布 */
    public final static int STATUS_PUBLISH_PUBLIC = 1;


    /** 页面类型 首页 */
    public final static String PAGE_TYPE_MAIN = "main";

    /** 页面类型 专辑一级页面 */
    public final static String PAGE_TYPE_ALBUM_1 = "album_1";

    /** 页面类型 专辑二级页面 */
    public final static String PAGE_TYPE_ALBUM_2 = "album_2";

    /** 页面类型 专辑详情 */
    public final static String PAGE_TYPE_ALBUM_DETAIL = "album_detail";

    /** 页面类型 视频一级页面 */
    public final static String PAGE_TYPE_VIDEO_1 = "video_1";

    /** 页面类型 视频二级页面 */
    public final static String PAGE_TYPE_VIDEO_2 = "video_2";

    /** 页面类型 视频列表 */
    public final static String PAGE_TYPE_VIDEO_FEED = "video_feed";

    /** 页面类型 本地页面 */
    public final static String PAGE_TYPE_LOCAL_1 = "local_1";


    /** intent key  */
    public final static String INTENT_KEY_NAVI_ID = "bundle_navi_id";

    /** intent key  */
    public final static String INTENT_KEY_NAVI_TYPE = "bundle_navi_type";

    /** intent key: 请求参数 */
    public final static String INTENT_KEY_REQUEST_PARAMS = "bundle_req_params";

    /**  */
    public final static String INTENT_KEY_PAGE_TYPE = "bundle_page_type";
}
