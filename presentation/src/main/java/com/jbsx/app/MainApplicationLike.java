package com.jbsx.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.jbsx.utils.SharedPreferencesProvider;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.UtilConstant;
import com.jbsx.view.login.util.LoginHelper;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by lijian15 on 2016/12/13.
 */

public class MainApplicationLike extends Application {
    private static UIThread mUiThread;
    private static MainApplicationLike sInstance;
    private static Context mContext;
    public static int statusBarHeight;
    private Handler mHanlder;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    // 微信的key，此key为线上版本的key，开发版不可用，发正式版本要用这个key
    public final static String APP_ID = "";

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mHanlder = new Handler();
        mUiThread = new UIThread();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = base;
        SharedPreferencesProvider.init(UtilConstant.PreferencesCP.SHARED_NAME, UtilConstant.PreferencesCP.AUTHORITY);
        LoginHelper.getInstance().readData();
//        MobSDK.init(base);
        getWXApi();
        getStatusHeight();
    }

    /**
     * 需要延迟初始化的
     * 依赖getApplicationContext
     */
    private void handleInitDelay() {
        getHanlder().post(new Runnable() {
            @Override
            public void run() {
//                initLeakChecker(getAppContext());
                MobSDK.init(getAppContext());
            }
        });
    }

    public static MainApplicationLike getInstance() {
        return sInstance;
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
        LeakCanary.install(this);
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
                int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    //根据资源ID获取响应的尺寸值
                    statusBarHeight1 = mContext.getResources().getDimensionPixelSize(resourceId);
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
            api = WXAPIFactory.createWXAPI(mContext, APP_ID);
            api.registerApp(APP_ID);
        }

        return api;
    }
}
