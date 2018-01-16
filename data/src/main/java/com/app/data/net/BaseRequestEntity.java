package com.app.data.net;

/**
 * Base Request
 *
 * Created by lijian15 on 2016/12/14.
 */

public class BaseRequestEntity {
    private String mUrl;
    private int mMethod;
    private BaseBody mBaseBody;

    /** The data that could be downloaded, such as some files. */
    private boolean mNeedByteData;

    public boolean needByteData() {
        return mNeedByteData;
    }

    public void setmNeedByteData(boolean mNeedByteData) {
        this.mNeedByteData = mNeedByteData;
    }

    public BaseBody getBaseBody() {
        return mBaseBody;
    }

    public void setBaseBody(BaseBody baseBody) {
        mBaseBody = baseBody;
    }

    public int getMethod() {
        return mMethod;
    }

    public void setMethod(int method) {
        mMethod = method;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
