package com.translatmaster.view.login.loginsdk.callback;

import com.translatmaster.view.login.loginsdk.model.AppErrorResult;
import com.translatmaster.view.login.loginsdk.model.AppFailResult;

/**
 * Created by lijian15 on 2017/11/9.
 */

public abstract class OnCommonCallbackApp {
    public abstract void onSuccess();
    public abstract void onError(AppErrorResult errorResult);
    public abstract void onFail(AppFailResult failResult);
}
