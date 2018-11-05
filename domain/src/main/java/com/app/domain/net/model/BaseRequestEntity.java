package com.app.domain.net.model;

/**
 * Base Request
 *
 * Created by lijian15 on 2016/12/14.
 */

public class BaseRequestEntity {
    private String mUrl;
    private int mMethod;
    private String functionId;
    private BaseBody mBaseBody;

    /** The data that could be downloaded, such as some files. */
    private boolean mNeedByteData;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

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
