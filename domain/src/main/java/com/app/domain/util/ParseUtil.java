package com.app.domain.util;

import com.google.gson.Gson;

/**
 * Created by lijian15 on 2018/1/9.
 */

public class ParseUtil {
    public static <T> T parseData(String json, Class<T> classObject) {
        T result = null;
        Gson gson = new Gson();
        try {
            result = gson.fromJson(json, classObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
