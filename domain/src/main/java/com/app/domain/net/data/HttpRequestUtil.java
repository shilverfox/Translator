package com.app.domain.net.data;

import android.text.TextUtils;

import com.app.domain.net.model.BaseHeader;
import com.app.domain.net.model.BaseRequestEntity;

public class HttpRequestUtil {
    public static void getHeader(BaseRequestEntity baseRequest, String token) {
        if (!TextUtils.isEmpty(token) && baseRequest != null) {
            BaseHeader header = new BaseHeader();
            header.add("X-token", token);

            baseRequest.setBaseHeader(header);
        }
    }
}
