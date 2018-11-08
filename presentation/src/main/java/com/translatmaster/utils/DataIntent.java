
package com.translatmaster.utils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.Hashtable;

public class DataIntent {
    public static Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
    private static long key = 0;

    public static String creatKey() {
        key = (System.currentTimeMillis() + key);
        return key + "";
    }

    public static void deleteObjectByKey(String key) {
        hashtable.remove(key);
    }

    //这里只作为传递activity数据使用   不能作为保存缓存数据使用
    public static Object get(String key) {
        Object obj = hashtable.get(key);
        hashtable.remove(key);
        return obj;
    }

    public static boolean isContainsKey(String key) {
        return hashtable.containsKey(key);
    }

    public static void put(String key, Object value) {
        hashtable.put(key, value);
    }

    public static void put(Intent intent, String KEY, Object value) {
        String key = DataIntent.creatKey();
        DataIntent.put(key, value);
        intent.putExtra(KEY, key);
        hashtable.put(key, value);
    }

    public static Object get(Intent intent, String KEY) {
        String key = intent.getStringExtra(KEY);
        Object obj = null;
        if (!TextUtils.isEmpty(key)) {
            obj = get(key);
        }
        return obj;
    }

    public static void put(Bundle intent, String KEY, Object value) {
        String key = DataIntent.creatKey();
        DataIntent.put(key, value);
        intent.putString(KEY, key);
        hashtable.put(key, value);
    }
}
