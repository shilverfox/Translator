package com.jbsx.view.login.callback;

import com.jbsx.view.login.data.LoginData;

public interface IRequestBindAccountCallback {
    void onBindFailed(String message);

    /** 绑定成功 */
    void onBindSuccessful(LoginData loginData);
}
