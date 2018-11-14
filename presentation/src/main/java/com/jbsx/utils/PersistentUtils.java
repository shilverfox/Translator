package com.jbsx.utils;


import android.content.Context;

/**
 * 本地存储工具类，通过contentProvider实现，便于后续跨进程使用
 */
public class PersistentUtils {

    public static final String SHARED_NAME = "PersistentUtils";

    /**
     * 搜索历史
     *
     * @param context
     * @return
     */
    public static String getSearcherHistory(Context context) {
        return SharePersistentUtils.getString(context, SHARED_NAME, UtilConstant.ShareConstant.SEARCH_HISTORY);
    }

    public static void saveSearcherHistory(Context context, String searcherHistory) {
        SharePersistentUtils.saveString(context, SHARED_NAME, UtilConstant.ShareConstant.SEARCH_HISTORY,
                searcherHistory);
    }

    public static void saveSign(Context context, String sign) {
        SharePersistentUtils.saveString(context, SHARED_NAME, UtilConstant.ShareConstant.HOME_SIGN, sign);
    }

    public static String getSign(Context context) {
        return SharePersistentUtils.getString(context, SHARED_NAME, UtilConstant.ShareConstant.HOME_SIGN);
    }

    // 保存搜索热词
    public static void saveHots(Context context, String key, String hots) {
        SharePersistentUtils.saveString(context, SHARED_NAME, key, hots);
    }

    // 获取搜索热词
    public static String getHots(Context context, String key) {
        return SharePersistentUtils.getString(context, SHARED_NAME, key);
    }
}
