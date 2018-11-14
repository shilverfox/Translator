package com.jbsx.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class FragmentUtil {

    /**
     * 检查生命周期
     *
     * @return
     */
    public static boolean checkLifeCycle(Activity activity, Fragment fragment) {
        return checkLifeCycle(activity, fragment, true);
    }

    /**
     * 检查生命周期
     *
     * @return
     */
    public static boolean checkLifeCycle(Activity activity, Fragment fragment, boolean isCheckFragment) {
        if (activity != null && !activity.isFinishing()) {
            if (!isCheckFragment) {
                return true;
            }
            if (fragment != null && !fragment.isDetached()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
