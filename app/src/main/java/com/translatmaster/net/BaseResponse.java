package com.translatmaster.net;

/**
 * Response data of http resuqst.
 *
 * Created by lijian15 on 2017/1/13.
 */

public class BaseResponse {
    private boolean mIsSuccessful;
    private String mContent;

    public boolean isSuccessful() {
        return mIsSuccessful;
    }

    public void setSuccessful(boolean successful) {
        mIsSuccessful = successful;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
