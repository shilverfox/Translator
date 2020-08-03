package com.app.domain.net.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lijian15 on 2017/2/7.
 */

public class BaseHeader {
    private HashMap<String, String> mData;

    public BaseHeader() {
        mData = new HashMap<String, String>();
    }

    public void add(String key, String value) {
        if (mData != null && !TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mData.put(key, value);
        }
    }

    public HashMap<String, String>getMapHeader() {
        return mData;
    }
}
