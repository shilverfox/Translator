package com.app.domain.net.model;

import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lijian15 on 2017/2/7.
 */

public class BaseBody {
    private HashMap<String, Object> mData;
    private String fileName;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BaseBody() {
        mData = new HashMap();
    }

    public void add(String key, Object value) {
        if (mData != null && !TextUtils.isEmpty(key) && value != null) {
            mData.put(key, value);
        }
    }

    public HashMap<String, Object>getMapBody() {
        return mData;
    }

    public String getStringBody() {
        if (mData != null) {
            StringBuilder mParams = new StringBuilder();

            Iterator<Map.Entry<String, Object>> it = mData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                mParams.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
            }

            return mParams.toString();
        } else {
            return null;
        }
    }
}
