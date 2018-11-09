package com.translatmaster.app;

/**
 * The father of the event that used by Event bus.
 *
 * Created by lijian15 on 2016/12/20.
 */

public class BaseEvent {
    public BaseEvent.LoginAction action;

    public enum LoginAction {
        CLOSE,
        FAIL,
        SUCCESS
    }

    public BaseEvent(BaseEvent.LoginAction action) {
        this.action = action;
    }
}
