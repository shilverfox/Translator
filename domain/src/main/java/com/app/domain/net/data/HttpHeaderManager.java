package com.app.domain.net.data;

public class HttpHeaderManager {
    private static HttpHeaderManager mInstance;

    private String deviceCode;
    private String orgCode;
    private String orgLogo;
    private String orgName;
    private String deviceId;

    private HttpHeaderManager() {
    }

    public static HttpHeaderManager getInstance() {
        if (mInstance == null) {
            mInstance = new HttpHeaderManager();
        }
        return mInstance;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
