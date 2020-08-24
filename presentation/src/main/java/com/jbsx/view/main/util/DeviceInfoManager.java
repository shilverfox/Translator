package com.jbsx.view.main.util;

import com.jbsx.utils.DeviceUtil;

/**
 * 设备管理器
 */
public class DeviceInfoManager {
    private static DeviceInfoManager mInstance;

    private String deviceCode;
    private String orgCode;
    private String orgLogo;
    private String orgName;
    private String deviceId;

    private DeviceInfoManager() {
    }

    public static DeviceInfoManager getInstance() {
        if (mInstance == null) {
            mInstance = new DeviceInfoManager();
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
        return DeviceUtil.getAndroidId();
    }
}
