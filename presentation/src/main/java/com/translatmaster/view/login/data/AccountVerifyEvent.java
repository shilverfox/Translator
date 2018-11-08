package com.translatmaster.view.login.data;

/**
 * 账号认证EventBus事件
 */

public class AccountVerifyEvent {
    private int mRequestSrc;
    private String mToken;

    public AccountVerifyEvent(int src) {
        mRequestSrc = src;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public int getRequestSrc() {
        return mRequestSrc;
    }
}
