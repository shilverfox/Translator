package com.jbsx.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static String parseString(JSONObject jsonObject, String key) {
        return parseString(jsonObject, key, "");
    }

    public static String parseString(JSONObject jsonObject, String key, String defalutValue) {
        try {
            if (jsonObject != null && !jsonObject.isNull(key)) {
                return jsonObject.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defalutValue;
    }

    public static int parseInt(JSONObject jsonObject, String key) {
        return parseInt(jsonObject, key, 0);
    }

    public static int parseInt(JSONObject jsonObject, String key, int defalutValue) {
        try {
            if (jsonObject != null && !jsonObject.isNull(key)) {
                return jsonObject.getInt(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defalutValue;
    }
    public static boolean parseBoolean(JSONObject jsonObject, String key) {
        return parseBoolean(jsonObject, key, false);
    }

    public static boolean parseBoolean(JSONObject jsonObject, String key, boolean defalutValue) {
        try {
            if (jsonObject != null && !jsonObject.isNull(key)) {
                return jsonObject.getBoolean(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defalutValue;
    }
}
