package com.translatmaster.view.login.callback;

import com.translatmaster.view.login.data.LoginResultEvent;

/**
 * 监听登陆结果，需要的页面去实现这个接口
 *
 * Created by lijian on 2018/11/10.
 */

public interface ILoginResultListener {
    void onMessageEvent(LoginResultEvent event);
}
