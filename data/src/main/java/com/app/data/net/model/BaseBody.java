package com.app.data.net.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lijian15 on 2017/2/7.
 */

public class BaseBody {
    private HashMap<String, String> mData;

    public BaseBody() {
        mData = new HashMap<String, String>();
    }

    public void add(String key, String value) {
        if (mData != null && !TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mData.put(key, value);
        }
    }

    public HashMap<String, String>getMapBody() {
        return mData;
    }

    public String getStringBody() {
        if (mData != null) {
            StringBuilder mParams = new StringBuilder();

            Iterator<Map.Entry<String, String>> it = mData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                mParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }

            return mParams.toString();
        } else {
            return null;
        }
    }
}
