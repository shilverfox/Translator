package com.jbsx.view.myinfo.data;

import com.jbsx.view.login.LoginActivity;
import com.jbsx.view.myinfo.activity.MyCommentActivity;
import com.jbsx.view.myinfo.activity.MyFavoriteActivity;
import com.jbsx.view.myinfo.activity.MyMessageActivity;
import com.jbsx.view.myinfo.activity.MyViewHistoryActivity;
import com.jbsx.view.setting.SettingsActivity;

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

    /** event bus事件，删除评论 */
    public final static int EVENT_BUS_DELETE_COMMENT = 1;

    public final static int[] MY_TYPE_IDS = {
            MY_TYPE_HISTORY, MY_TYPE_FAVORITY, MY_TYPE_COMMENT, MY_TYPE_MESSAGE, MY_TYPE_SETTING
    };

    public final static String[] MY_TYPE_NAMES = {
            "观看历史", "我的收藏", "我的评论", "我的消息", "系统设置"
    };

    public final static Class[] MY_TYPE_NAVIGATIONS = {
            MyViewHistoryActivity.class, MyFavoriteActivity.class, MyCommentActivity.class, MyMessageActivity.class, SettingsActivity.class,
    };
}
