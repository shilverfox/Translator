/**
 *
 */
package com.jbsx.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Router {
    public static final String COMMENT_DETAIL_KEY = "commentDetail";

    private static final Router router = new Router();

    public static Router getInstance() {
        return router;
    }

    public void openByNewTask(Class activityClass, Context context) {
        openByContext(activityClass, context, null, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public void open(String activityClass, Activity context) {
        open(activityClass, context, null, -1);
    }

    public void open(Class activityClass, Activity context) {
        open(activityClass, context, null, -1);
    }

    public void open(String activityClass, Activity context, Bundle extras) {
        open(activityClass, context, extras, -1);
    }

    public void open(Class activityClass, Activity context, Bundle extras) {
        open(activityClass, context, extras, -1);
    }

    public void open(String activityClass, Context context, Bundle extras) {
        openByContext(activityClass, context, extras, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public void open(Class activityClass, Context context, Bundle extras) {
        openByContext(activityClass, context, extras, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public void open(String activityClass, Activity context, int flags) {
        openByContext(activityClass, context, null, flags);
    }

    public void open(Class activityClass, Activity context, int flags) {
        openByContext(activityClass, context, null, flags);
    }

    public void open(String activityClass, Activity context, Bundle extras, int flags) {
        openByContext(activityClass, context, extras, flags);
    }

    public void open(Class activityClass, Activity context, Bundle extras, int flags) {
        openByContext(activityClass, context, extras, flags);
    }

    private void openByContext(Class activityClass, Context context, Bundle extras, int flags) {
        if (context == null) return;
        Intent intent = new Intent();
        intent.setClass(context, activityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (flags != -1) {
            intent.addFlags(flags);
        }
        context.startActivity(intent);
    }

    private void openByContext(String activityClass, Context context, Bundle extras, int flags) {
        if (context == null) return;
        Intent intent = new Intent();
        try {
            Class aClass = Class.forName(activityClass);
            intent.setClass(context, aClass);
            if (extras != null) {
                intent.putExtras(extras);
            }
            if (flags != -1) {
                intent.addFlags(flags);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
