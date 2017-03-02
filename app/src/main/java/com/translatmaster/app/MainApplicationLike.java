package com.translatmaster.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by lijian15 on 2016/12/13.
 */

// For tinke
@DefaultLifeCycle(
        application = "com.translatmaster.app.MainApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL)

public class MainApplicationLike extends DefaultApplicationLike {
    private static Context mContext;

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mContext = this.getApplicationContext();
//
//        // For memory leakage checking.
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//    }



    public MainApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                               long applicationStartElapsedTime, long applicationStartMillisTime,
                               Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        mContext = base;

        // For tinker
        MultiDex.install(base);
        TinkerInstaller.install(this);

        // For memory leakage checking.
        if (LeakCanary.isInAnalyzerProcess(base)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
//        LeakCanary.install(getApplication());
    }

    public static Context getAppContext() {
        return mContext;
    }
}
