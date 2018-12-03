package com.jbsx.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.jbsx.utils.SharedPreferencesProvider;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.UtilConstant;
import com.jbsx.view.login.util.LoginHelper;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by lijian15 on 2016/12/13.
 */

// For tinke
@DefaultLifeCycle(
        application = "com.jbsx.app.MainApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL)

public class MainApplicationLike extends DefaultApplicationLike {
    private static Context mContext;
    private static UIThread mUiThread;
    private static MainApplicationLike sInstance;
    public static int statusBarHeight;
    private Handler mHanlder;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    // 微信的key，此key为线上版本的key，开发版不可用，发正式版本要用这个key
    public final static String APP_ID = "";

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
        sInstance = this;
        mHanlder = new Handler();
        mContext = base;
        mUiThread = new UIThread();

        SharedPreferencesProvider.init(UtilConstant.PreferencesCP.SHARED_NAME, UtilConstant.PreferencesCP.AUTHORITY);
        LoginHelper.getInstance().readData();
        getWXApi();
        initHotFix(base);
        getStatusHeight();
//        initLeakChecker(base);
    }

    public static MainApplicationLike getInstance() {
        return sInstance;
    }

    /**
     * For tinker
      */
    private void initHotFix(Context base) {
        MultiDex.install(base);
        TinkerInstaller.install(this);
    }

    /**
     * For memory leakage checking.
     *
     * @param base
     */
    private void initLeakChecker(Context base) {
        if (LeakCanary.isInAnalyzerProcess(base)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static UIThread getUiThread() {
        return mUiThread;
    }

    public void getStatusHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int statusBarHeight1 = -1;
            try {
                int resourceId = getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    //根据资源ID获取响应的尺寸值
                    statusBarHeight1 = getAppContext().getResources().getDimensionPixelSize(resourceId);
                }
            } catch (Exception e) {
                statusBarHeight1 = UiTools.dip2px(24);
                e.printStackTrace();
            }

            statusBarHeight = statusBarHeight1;
        }
    }

    public Handler getHanlder() {
        return mHanlder;
    }

    public IWXAPI getWXApi() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(getAppContext(), APP_ID);
            api.registerApp(APP_ID);
        }

        return api;
    }
}
