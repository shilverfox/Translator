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
        CELEBRITY_TYPE_ZHUZHE,
        CELEBRITY_TYPE_JIESHUO,
        CELEBRITY_TYPE_ZHUANGAO,
        CELEBRITY_TYPE_ZHUCHI,
        CELEBRITY_TYPE_ZHUJIANG,
        CELEBRITY_TYPE_ZRBJ,
        CELEBRITY_TYPE_ZONGBJ,
        CELEBRITY_TYPE_XSYSGW,
        CELEBRITY_TYPE_ZONGGW,
        CELEBRITY_TYPE_JIANZHI,
        CELEBRITY_TYPE_ZONGJIANZHI
    };

    /** 剧组角色列表，主要角色，用于播放页面的人员显示 */
    public final static int[] CREW_MAIN = {CELEBRITY_TYPE_DAOYAN, CELEBRITY_TYPE_ZHUANGAO, CELEBRITY_TYPE_ZHUCHI};
}
