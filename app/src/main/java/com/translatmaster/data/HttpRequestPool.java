package com.translatmaster.data;

import com.translatmaster.net.BaseRequestEntity;

/**
 * List all the http request.
 *
 * Created by lijian15 on 2016/12/14.
 */

public class HttpRequestPool {
    public static BaseRequestEntity getTranslateResult(String url) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(url);

        return baseRequest;
    }
}
