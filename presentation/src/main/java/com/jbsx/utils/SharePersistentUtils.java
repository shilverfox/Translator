package com.jbsx.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 轻量持久化，往SharedPreferences存储读取数据，跨进程变量会存入数据库
 * 
 */
public class SharePersistentUtils {

    private static final String TAG = SharePersistentUtils.class.getSimpleName();

    private static String AUTHORITY = null; // provider的权限名
    private static String SHARED_NAME = ""; // SharedPreference里存储的

    public static Uri getBaseUri() {
        if (AUTHORITY == null) {
            throw new RuntimeException("Don't have init SharePersistentUtils in Application");
        }
        return Uri.parse("content://" + AUTHORITY + "/");
    }

    public static void init(String authority) {
        AUTHORITY = authority;
    }

    public static void init(String shareName, String authority) {
        AUTHORITY = authority;
        SHARED_NAME = shareName;
    }

    /**
     * @deprecated 可以跨进程保存boolean值到sharedpreference
     * @param context
     * @param preName
     *            Preferences名字(该参数已废弃)
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String preName, String key, boolean value) {
        if (context == null || TextUtils.isEmpty(key)) {
            return;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_BOOLEAN);
        ContentValues values = new ContentValues();
        values.put(UtilConstant.PreferencesCP.KEY, key);
        values.put(UtilConstant.PreferencesCP.VALUE_BOOLEAN, value);

        // 添加存储的文件名
        if (SHARED_NAME != null) {
            values.put(UtilConstant.PreferencesCP.SHARED_NAME, SHARED_NAME);
        }

        try {
            context.getContentResolver().update(uri, values, null, null);
        } catch (Exception e) {
            LogTools.e(TAG, e.toString());
        }

    }

    /**
     * 可以跨进程保存boolean值到sharedpreference
     * 
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        saveBoolean(context, "", key, value);
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param preName
     *            Preferences名字(该参数已废弃)
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String preName, String key) {
        return getBoolean(context, preName, key, false);
    }

    /**
     * @deprecated 可以跨进程从sharedpreference读取boolean值
     * @param context
     * @param preName
     *            Preferences名字(该参数已废弃)
     * @param key
     * @param defValue
     *            Value to return if this preference does not exist.
     * @return
     */
    public static boolean getBoolean(Context context, String preName, String key, boolean defValue) {
        if (context == null || TextUtils.isEmpty(key)) {
            return defValue;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_BOOLEAN);
        // 添加存储的文件名
        String projection[] = new String[] { key, String.valueOf(defValue), SHARED_NAME };
        Cursor c = null;
        boolean value = defValue;
        try {
            c = context.getContentResolver().query(uri, projection, null, null, null);
            if (c != null && c.moveToFirst()) {
                int intValue = c.getInt(c.getColumnIndex(UtilConstant.PreferencesCP.VALUE));
                value = intValue == 0 ? false : true;
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return value;
    }

    /**
     * 可以跨进程从sharedpreference读取boolean值
     * 
     * @param context
     * @param key
     * @param defValue
     *            Value to return if this preference does not exist.
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getBoolean(context, "", key, defValue);
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, "", key);
    }

    /**
     * @deprecated 可以跨进程保存String值到sharedpreference
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     * @param value
     */
    public static void saveString(Context context, String preName, String key, String value) {
        if (context == null || TextUtils.isEmpty(key)) {
            return;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_STRING);
        ContentValues values = new ContentValues();
        values.put(UtilConstant.PreferencesCP.KEY, key);
        values.put(UtilConstant.PreferencesCP.VALUE_STRING, value);

        // 添加存储的文件名
        if (SHARED_NAME != null) {
            values.put(UtilConstant.PreferencesCP.SHARED_NAME, SHARED_NAME);
        }
        try {
            context.getContentResolver().update(uri, values, null, null);
        } catch (Exception e) {
            LogTools.e(TAG, e.toString());
        }
    }

    /**
     * 可以跨进程保存String值到sharedpreference
     * 
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        saveString(context, "", key, value);
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     * @return
     */
    public static String getString(Context context, String preName, String key) {
        return getString(context, preName, key, "");
    }

    /**
     * @deprecated 可以跨进程从sharedpreference读取string值
     * @param context
     * @param preName
     * @param key
     *            Preferences文件名(该参数已废弃)
     * @param defValue
     *            默认值
     * @return
     */
    public static String getString(Context context, String preName, String key, String defValue) {
        if (context == null || TextUtils.isEmpty(key)) {
            return defValue;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_STRING);
        // 添加存储的文件名
        String projection[] = new String[] { key, defValue, SHARED_NAME };
        Cursor c = null;
        String value = defValue;
        try {
            c = context.getContentResolver().query(uri, projection, null, null, null);
            if (c != null && c.moveToFirst()) {
                value = c.getString(c.getColumnIndex(UtilConstant.PreferencesCP.VALUE));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return value;
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        return getString(context, "", key);
    }

    /**
     * 可以跨进程从sharedpreference读取string值
     * 
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getStringWithValue(Context context, String key, String defValue) {
        return getString(context, "", key, defValue);
    }

    /**
     * @deprecated 可以跨进程保存int值到sharedpreference
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String preName, String key, int value) {
        if (context == null || TextUtils.isEmpty(key)) {
            return;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_INT);
        ContentValues values = new ContentValues();
        values.put(UtilConstant.PreferencesCP.KEY, key);
        values.put(UtilConstant.PreferencesCP.VALUE_INT, value);

        // 添加存储的文件名
        if (SHARED_NAME != null) {
            values.put(UtilConstant.PreferencesCP.SHARED_NAME, SHARED_NAME);
        }

        try {
            context.getContentResolver().update(uri, values, null, null);
        } catch (Exception e) {
            LogTools.e(TAG, e.toString());
        }
    }

    /**
     * 可以跨进程保存int值到sharedpreference
     * 
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        saveInt(context, "", key, value);
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param preName
     * @param key
     * @return
     */
    public static int getInt(Context context, String preName, String key) {
        return getInt(context, preName, key, 0);
    }

    /**
     * @deprecated 可以跨进程从sharedpreference读取int值
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     * @param defValue
     *            获取不到数据后得到的一个默认值
     * @return
     */
    public static int getInt(Context context, String preName, String key, int defValue) {
        if (context == null || TextUtils.isEmpty(key)) {
            return defValue;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_INT);
        // 添加存储的文件名
        String projection[] = new String[] { key, String.valueOf(defValue), SHARED_NAME };
        Cursor c = null;
        int value = defValue;
        try {
            c = context.getContentResolver().query(uri, projection, null, null, null);
            if (c != null && c.moveToFirst()) {
                value = c.getInt(c.getColumnIndex(UtilConstant.PreferencesCP.VALUE));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return value;
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key) {
        return getInt(context, "", key);
    }

    /**
     * 可以跨进程从sharedpreference读取int值
     * 
     * @param context
     * @param key
     * @param defValue
     *            获取不到数据后得到的一个默认值
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        return getInt(context, "", key, defValue);
    }

    /**
     * @deprecated 可以跨进程保存long值到sharedpreference
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     * @param value
     *            获取不到数据后得到的一个默认值
     */
    public static void saveLong(Context context, String preName, String key, long value) {
        if (context == null || TextUtils.isEmpty(key)) {
            return;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_LONG);
        ContentValues values = new ContentValues();
        values.put(UtilConstant.PreferencesCP.KEY, key);
        values.put(UtilConstant.PreferencesCP.VALUE_LONG, value);

        // 添加存储的文件名
        if (SHARED_NAME != null) {
            values.put(UtilConstant.PreferencesCP.SHARED_NAME, SHARED_NAME);
        }
        try {
            context.getContentResolver().update(uri, values, null, null);
        } catch (Exception e) {
            LogTools.e(TAG, e.toString());
        }
    }

    /**
     * 可以跨进程保存long值到sharedpreference
     * 
     * @param context
     * @param key
     * @param value
     *            获取不到数据后得到的一个默认值
     */
    public static void saveLong(Context context, String key, long value) {
        saveLong(context, "", key, value);
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     * @return
     */
    public static long getLong(Context context, String preName, String key) {
        return getLong(context, preName, key, 0);
    }

    /**
     * @deprecated 可以跨进程从sharedpreference读取long值
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     * @param defValue
     *            默认值
     * @return
     */
    public static long getLong(Context context, String preName, String key, long defValue) {
        if (context == null || TextUtils.isEmpty(key)) {
            return defValue;
        }
        Uri uri = Uri.withAppendedPath(getBaseUri(), UtilConstant.PreferencesCP.TYPE_LONG);
        // 添加存储的文件名
        String projection[] = new String[] { key, String.valueOf(defValue), SHARED_NAME };
        Cursor c = null;
        long value = defValue;
        try {
            c = context.getContentResolver().query(uri, projection, null, null, null);
            if (c != null && c.moveToFirst()) {
                value = c.getLong(c.getColumnIndex(UtilConstant.PreferencesCP.VALUE));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return value;
    }

    /**
     * 可以跨进程从sharedpreference读取long值
     * 
     * @param context
     * @param key
     * @param defValue
     *            默认值
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        return getLong(context, "", key, defValue);
    }

    /**
     * @deprecated 不要使用该函数
     * @param context
     * @param key
     * @return
     */
    public static long getLong(Context context, String key) {
        return getLong(context, "", key);
    }

    /**
     * @deprecated 从sharedPerference中移除某个key
     * @param context
     * @param preName
     *            Preferences文件名
     * @param key
     */
    public static void removePerference(Context context, String preName, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return;
        }
        // 添加存储的文件名
        String selectionArgs[] = new String[] { key, SHARED_NAME };
        try {
            context.getContentResolver().delete(getBaseUri(), "key = ?", selectionArgs);
        } catch (Exception e) {
            LogTools.e(TAG, e.toString());
        }

    }

    /**
     * 从sharedPerference中移除某个key
     * 
     * @param context
     * @param key
     */
    public static void removePerference(Context context, String key) {
        removePerference(context, "", key);
    }

}
