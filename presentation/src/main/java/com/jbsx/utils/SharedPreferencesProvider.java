package com.jbsx.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 跨进程读取SharedPreferences
 * <p>
 * 通过SharePersistentUtils工具进行读和写SharedPreferencesProvider时，同时要把要操作的文件名传过来 <br/>
 * 1. 如果调用query()方法，在projection里添加SharedName <br/>
 * 2. 如果调用update()方法，在ContentValues里添加SharedName <br/>
 * 3. 如果调用delete()方法，在String[] selectionArgs里添加SharedName
 *
 * @// TODO: 2018/11/15 别忘了在manifext中配置， authorities定义在UtilConstant类
 *         <provider
android:name=".utils.SharedPreferencesProvider"
android:authorities="com.jbsx.android.provider.preference"
android:exported="false" />
 */
public class SharedPreferencesProvider extends ContentProvider {

    private static String SHARED_NAME = ""; // 跨进程访问的sharedpreference文件名
    private static UriMatcher sURIMatcher;

    public static void init(String shareName, String authority) {
        SharePersistentUtils.init(authority);
        SHARED_NAME = shareName;
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(authority, UtilConstant.PreferencesCP.TYPE_BOOLEAN, UtilConstant.PreferencesCP.CODE_BOOLEAN);
        sURIMatcher.addURI(authority, UtilConstant.PreferencesCP.TYPE_INT, UtilConstant.PreferencesCP.CODE_INT);
        sURIMatcher.addURI(authority, UtilConstant.PreferencesCP.TYPE_LONG, UtilConstant.PreferencesCP.CODE_LONG);
        sURIMatcher.addURI(authority, UtilConstant.PreferencesCP.TYPE_STRING, UtilConstant.PreferencesCP.CODE_STRING);

    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // 如果传入了Shared_Name就使用传入的SharedPerference的文件名称，如果没有传入，就使用默认的SHARED_NAME
        String sharedName = SHARED_NAME;
        if (projection.length >= 3 && !TextUtils.isEmpty(projection[2])) {
            sharedName = projection[2]; // 注意，已经固定为第三个元素为Shared_name了
        }

        SharedPreferences sp = getContext().getSharedPreferences(sharedName, 0);
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{UtilConstant.PreferencesCP.VALUE});
        Object[] itemValues = new Object[1];
        try {
            switch (sURIMatcher.match(uri)) {
                case UtilConstant.PreferencesCP.CODE_BOOLEAN:
                    boolean boolDefValue = Boolean.parseBoolean(projection[1]);
                    boolean boolValue = sp.getBoolean(projection[0], boolDefValue);
                    if (boolValue) {
                        itemValues[0] = 1;
                    } else {
                        itemValues[0] = 0;
                    }
                    break;
                case UtilConstant.PreferencesCP.CODE_INT:
                    int intDefValue = 0;
                    try {
                        intDefValue = Integer.parseInt(projection[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int intValue = sp.getInt(projection[0], intDefValue);
                    itemValues[0] = intValue;
                    break;
                case UtilConstant.PreferencesCP.CODE_LONG:
                    long longDefValue = 0;
                    try {
                        longDefValue = Long.parseLong(projection[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    long longValue = sp.getLong(projection[0], longDefValue);
                    itemValues[0] = longValue;
                    break;
                case UtilConstant.PreferencesCP.CODE_STRING:
                    String stringValue = sp.getString(projection[0], projection[1]);
                    itemValues[0] = stringValue;
                    break;
                default:
                    return null;
            }
            matrixCursor.addRow(itemValues);
            return matrixCursor;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 如果传入了Shared_Name就使用传入的SharedPerference的文件名称，如果没有传入，就使用默认的SHARED_NAME
        String sharedName = SHARED_NAME;
        if (selectionArgs.length >= 2 && !TextUtils.isEmpty(selectionArgs[1])) {
            sharedName = selectionArgs[1]; // 注意，已经固定为第三个元素为Shared_name了
        }

        SharedPreferences sp = getContext().getSharedPreferences(sharedName, 0);
        Editor editor = sp.edit();
        editor.remove(selectionArgs[0]);
        editor.commit();
        return 1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // 如果传入了Shared_Name就使用传入的SharedPerference的文件名称，如果没有传入，就使用默认的SHARED_NAME
        String sharedName = values.getAsString(UtilConstant.PreferencesCP.SHARED_NAME); // 注意，已经固定为第三个元素为Shared_name了
        if (TextUtils.isEmpty(sharedName)) {
            sharedName = SHARED_NAME;
        }

        SharedPreferences sp = getContext().getSharedPreferences(sharedName, 0);
        Editor editor = sp.edit();
        String key = values.getAsString(UtilConstant.PreferencesCP.KEY);
        switch (sURIMatcher.match(uri)) {
            case UtilConstant.PreferencesCP.CODE_BOOLEAN:
                editor.putBoolean(key, values.getAsBoolean(UtilConstant.PreferencesCP.VALUE_BOOLEAN));
                break;
            case UtilConstant.PreferencesCP.CODE_INT:
                editor.putInt(key, values.getAsInteger(UtilConstant.PreferencesCP.VALUE_INT).intValue());
                break;
            case UtilConstant.PreferencesCP.CODE_LONG:
                editor.putLong(key, values.getAsLong(UtilConstant.PreferencesCP.VALUE_LONG).longValue());
                break;
            case UtilConstant.PreferencesCP.CODE_STRING:
                editor.putString(key, values.getAsString(UtilConstant.PreferencesCP.VALUE_STRING));
                break;
        }
        editor.commit();
        return 1;
    }

}
