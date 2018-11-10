package com.translatmaster.view.login.thirdapi;

/**
 * 调用第三方登录结果，EventBus通知对应的登录view
 */

public class LoginThirdResultEvent {
    public final static int THIRD_API_EVENT_AGREE = 1;
    public final static int THIRD_API_EVENT_DENY = 2;
    public final static int THIRD_API_EVENT_CANCEL = 3;

    private String mCode;

    public LoginThirdResultEvent(String code) {
        mCode = code;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }
}
