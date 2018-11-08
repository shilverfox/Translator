package com.translatmaster.view.login.callback;

import com.translatmaster.view.login.data.LoginData;

public interface IRequestBindAccountCallback {
    void onBindFailed(String message);

    /** 绑定成功 */
    void onBindSuccessful(LoginData loginData);
}
