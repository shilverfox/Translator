package com.jbsx.view.login.loginsdk.callback;

/**
 * Created by lijian15 on 2017/11/9.
 */

public abstract class OnDataCallbackApp<T> extends OnCommonCallbackApp {
    public final void onSuccess() {
    }

    public abstract void onSuccess(T var1);
}
