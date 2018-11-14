package com.jbsx.view.login.data;

public class LoginUpdate {

    private boolean IsSuccess, isLoginOut;

    public LoginUpdate(boolean isSuccess) {
        IsSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public boolean isLoginOut() {
        return isLoginOut;
    }

    public void setLoginOut(boolean loginOut) {
        isLoginOut = loginOut;
    }
}
