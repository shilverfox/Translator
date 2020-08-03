package com.jbsx.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

/**
 * Activity状态检查
 * 比如是否销毁
 *
 * Created by lijian15 on 2017/8/11.
 */

public class ActivityStatusChecker {
    private final static byte TYPE_ERROR = 0;
    private final static byte TYPE_CONTEXT_ACTIVITY = 1;
    private final static byte TYPE_CONTEXT_FRAGMENT_ACTIVITY = 2;

    private static byte getActivityType(Context context) {
        if (context == null) {
            return TYPE_ERROR;
        }

        if (context instanceof Activity) {
            return TYPE_CONTEXT_ACTIVITY;
        }

        if (context instanceof FragmentActivity) {
            return TYPE_CONTEXT_FRAGMENT_ACTIVITY;
        }

        return TYPE_ERROR;
    }
    /**
     * 判断Activity是否已经销毁
     *
     * @param context
     * @return
     */
    public static boolean hasActivityBeenDestroyed(Context context) {
        byte type = getActivityType(context);

        if (context == null || type == TYPE_ERROR) {
            return true;
        } else {
            Activity activity = null;

            if (type == TYPE_CONTEXT_ACTIVITY) {
                activity = (Activity)context;
            } else if (type == TYPE_CONTEXT_FRAGMENT_ACTIVITY) {
                activity = (FragmentActivity)context;
            } else {
                activity = (Activity)context;
            }

            boolean hasBeenDestroyed = activity.isFinishing();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                hasBeenDestroyed = (hasBeenDestroyed || activity.isDestroyed());
            }

            return hasBeenDestroyed;
        }
    }
}
