package com.jbsx.view.login.loginsdk.model;

/**
 * Created by lijian15 on 2017/11/9.
 */

public class AppErrorResult {
    private int errorCode;
    private String errorMsg;

    public AppErrorResult(int var1, String var2) {
        this.errorCode = var1;
        this.errorMsg = var2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String toString() {
        return this.errorCode + "," + this.errorMsg;
    }
}
