package com.translatmaster.view.login.data;

import com.translatmaster.app.BaseEvent;

/**
 * 登陆成功
 */
public class LoginResultEvent extends BaseEvent {
    public LoginResultEvent.LoginAction action;

    public enum LoginAction {
        CLOSE,
        FAIL,
        SUCCESS
    }

    public LoginResultEvent(LoginResultEvent.LoginAction action) {
        this.action = action;
    }
}
