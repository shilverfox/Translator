package com.jbsx.view.myinfo.data;

import com.jbsx.player.PlayerActivity;
import com.jbsx.view.login.LoginActivity;
import com.jbsx.view.login.ModifyPasswordActivity;
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

    /** event bus事件，增加评论 */
    public final static int EVENT_BUS_ADD_COMMENT = 2;

    /** event bus事件，查看详情 */
    public final static int EVENT_BUS_VIEW_DETAIL = 3;

    public final static int[] MY_TYPE_IDS = {
            MY_TYPE_HISTORY, MY_TYPE_FAVORITY, MY_TYPE_COMMENT, MY_TYPE_MESSAGE, MY_TYPE_SETTING
    };

    public final static int[] MY_INFO_IDS = {
            MY_TYPE_HISTORY, MY_TYPE_FAVORITY
    };

    public final static String[] MY_TYPE_NAMES = {
            "观看历史", "我的收藏", "我的评论", "我的消息", "系统设置"
    };

    public final static String[] MY_INFO_NAMES = {
            "昵称", "修改密码"
    };

    public final static Class[] MY_TYPE_NAVIGATIONS = {
            MyViewHistoryActivity.class, MyFavoriteActivity.class, MyCommentActivity.class, MyMessageActivity.class, SettingsActivity.class,
    };

    public final static Class[] MY_INFO_NAVIGATIONS = {
            MyViewHistoryActivity.class, ModifyPasswordActivity.class
    };

    public final static boolean[] NEED_LOGIN = {true, true, true, true, false};
}
