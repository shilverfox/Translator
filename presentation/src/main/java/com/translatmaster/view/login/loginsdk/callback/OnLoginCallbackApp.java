package com.translatmaster.view.login.loginsdk.callback;

import com.translatmaster.view.login.loginsdk.model.AppFailResult;
import com.translatmaster.view.login.loginsdk.model.AppPicDataInfo;

/**
 * Created by lijian15 on 2017/11/9.
 */

public abstract class OnLoginCallbackApp extends OnCommonCallbackApp {
    public abstract void onFail(AppFailResult failResult, AppPicDataInfo picDataInfo);
}
