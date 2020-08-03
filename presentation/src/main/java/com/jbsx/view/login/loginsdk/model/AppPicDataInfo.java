package com.jbsx.view.login.loginsdk.model;

/**
 * Created by lijian15 on 2017/11/9.
 */

public class AppPicDataInfo {
    private String stEncryptKey;
    private byte[] sPicData;
    private String authCode;

    public String getStEncryptKey() {
        return stEncryptKey;
    }

    public void setStEncryptKey(String stEncryptKey) {
        this.stEncryptKey = stEncryptKey;
    }

    public byte[] getsPicData() {
        return sPicData;
    }

    public void setsPicData(byte[] sPicData) {
        this.sPicData = sPicData;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
