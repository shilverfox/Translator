package com.translatmaster.view.myinfo.data;

import com.translatmaster.view.login.LoginActivity;
import com.translatmaster.view.myinfo.MyInfoActivity;
import com.translatmaster.view.setting.SettingsActivity;

public class MyInfoConst {
    /** 我的页面item id：历史 */
    public final static int MY_TYPE_HISTORY = 1;

    /** 我的页面item id：收藏 */
    public final static int MY_TYPE_FAVORITY = 2;

    /** 我的页面item id：评论 */
    public final static int MY_TYPE_COMMENT = 3;

    /** 我的页面item id：消息 */
    public final static int MY_TYPE_MESSAGE = 4;

    /** 我的页面item id：设置 */
    public final static int MY_TYPE_SETTING = 5;

    public final static int[] MY_TYPE_IDS = {
            MY_TYPE_HISTORY, MY_TYPE_FAVORITY, MY_TYPE_COMMENT, MY_TYPE_MESSAGE, MY_TYPE_SETTING
    };

    public final static String[] MY_TYPE_NAMES = {
            "观看历史", "我的收藏", "我的评论", "我的消息", "系统设置"
    };

    public final static Class[] MY_TYPE_NAVIGATIONS = {
            MyInfoActivity.class, MyInfoActivity.class, LoginActivity.class, LoginActivity.class, SettingsActivity.class,
    };
}
