package com.app.domain.net.repository;

import com.app.data.net.BaseRequestEntity;
import com.app.data.net.BaseResponse;
import com.app.data.net.RequestManager;

/**
 * Http request
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskDataSourceImpl implements TaskDataSource {
    @Override
    public BaseDominData requestTranslate(String content, String src, String dest) {
        BaseDominData dominData = new BaseDominData();
        dominData.setBaseResponse(RequestManager.setRequestForRx(HttpRequestPool.getTranslateResult(content, src, dest)));

        return dominData;
//        return RequestManager.setRequestForRx(HttpRequestPool.mockLocation("http://adash.m.taobao.com/rest/sur?ak=21811227&av=3.0.0&c=10004852&v=3.0&s=e339dd7bf87a6cba609e56e9506108e3d02ae33f&d=V9%2B0VkEIMsUDAFPyuDC6WP7R&sv=4.4.1&p=a&t=1486435258768&u=&is=1"));
    }

    @Override
    public BaseDominData getHotFixPatch() {
        return null;
    }
}
