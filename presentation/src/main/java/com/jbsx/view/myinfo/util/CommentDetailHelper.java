package com.jbsx.view.myinfo.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.jbsx.utils.Router;
import com.jbsx.view.myinfo.activity.CommentDetailActivity;
import com.jbsx.view.myinfo.data.UserComments;

public class CommentDetailHelper {
    /**
     * 跳转到评论详情
     *
     * @param activity
     * @param userComments
     */
    public static void goToCommentDetailActivity(Activity activity, UserComments userComments) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Router.COMMENT_DETAIL_KEY, userComments);
        Router.getInstance().open(CommentDetailActivity.class, activity, bundle);
    }
}
