package com.translatmaster.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by lijian15 on 2016/12/13.
 */

public class MainApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = this.getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
