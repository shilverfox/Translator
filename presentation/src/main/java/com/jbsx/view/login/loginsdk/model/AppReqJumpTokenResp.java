package com.jbsx.view.login.loginsdk.model;

/**
 * Created by lijian15 on 2017/11/9.
 */

public class AppReqJumpTokenResp {
    private String url;
    private String token;

    /** 按规则生成，非sdk返回字段 */
    private String formattedUrl;

    public String getFormattedUrl() {
        return formattedUrl;
    }

    public void setFormatedUrl(String formattedUrl) {
        this.formattedUrl = formattedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
