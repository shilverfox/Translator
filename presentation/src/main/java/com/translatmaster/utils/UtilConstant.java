package com.translatmaster.utils;


import android.net.Uri;

/**
 * Util中用到的所有常量
 */
public final class UtilConstant {

    public static final class PreferencesCP {
        public static final String AUTHORITY = "com.jd.android.provider.preference";
        public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY + "/");
        public static final String TYPE_BOOLEAN = "boolean";
        public static final int CODE_BOOLEAN = 1;
        public static final String TYPE_INT = "int";
        public static final int CODE_INT = 2;
        public static final String TYPE_LONG = "long";
        public static final int CODE_LONG = 3;
        public static final String TYPE_STRING = "string";
        public static final int CODE_STRING = 4;

        // 存储SharedPerences里文件的名称
        public static final String SHARED_NAME = "shared_name";

        public static final String KEY = "key";
        public static final String VALUE = "value";
        public static final String VALUE_BOOLEAN = "value_boolean";
        public static final String VALUE_INT = "value_int";
        public static final String VALUE_LONG = "value_long";
        public static final String VALUE_STRING = "value_string";
    }

    public static final class ShareConstant {
        public static final String SEARCH_HISTORY = "searcher_history";
        public static final String HOME_MD5_CONTENT = "home_md5_content";
        public static final String Order_MD5_CONTENT="order_md5_content";
        public static final String PULL_ADV_TIME="pull_adv_time";
        public static final String HOME_SIGN="home_sign";
        public static final String PLUNGIN_DATAVERSION="plungin_dataversion";
    }
}
