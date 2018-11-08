package com.translatmaster.weixin;

/**
 * 一般微信Event Bus，在未登录情况下，微信也默认发送COMMAND_SENDMESSAGE_TO_WX code，
 * 页面可以根据需要响应该Event，比如停掉ProgressBar
 *
 * Created by lijian15 on 2016/11/9.
 */

public class WechatEvent {
    private String mErrMessage;

    public WechatEvent(String message) {
        mErrMessage = message;
    }

    public String getErrorMessage() {
        return mErrMessage;
    }
}
