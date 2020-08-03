package com.jbsx.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jbsx.app.MainApplicationLike;

/**
 * Created by lijian15 on 2017/3/8.
 */

public class SystemUtil {

    /**
     * Get the version of app
     *
     * @return
     */
    public static String getAppVersion() {
        Context context = MainApplicationLike.getAppContext();
        String version = null;

        if (context != null) {
            PackageManager packageManager = context.getPackageManager();

            try {
                PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                version = packInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // Nothing to do
            }
        }

        return version;
    }
}
