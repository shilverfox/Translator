package com.app.domain.net.data;

import com.app.domain.net.model.BaseHeader;
import com.app.domain.net.model.BaseRequestEntity;

public class HttpRequestUtil {
    public static void getHeader(BaseRequestEntity baseRequest, String token) {
        if (baseRequest != null) {
            BaseHeader header = new BaseHeader();
            header.add("orgCode", ConstData.ORG_CODE);
            header.add("deviceId", HttpHeaderManager.getInstance().getDeviceId());
            header.add("deviceCode", HttpHeaderManager.getInstance().getDeviceCode());

            baseRequest.setBaseHeader(header);
        }
    }
}
