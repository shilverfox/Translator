package com.translatmaster.view.main.task;

import com.translatmaster.data.ConstData;
import com.translatmaster.data.HttpRequestPool;
import com.translatmaster.net.BaseResponse;
import com.translatmaster.net.RequestManager;

/**
 * Http request
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskDataSourceImpl implements TaskDataSource {
    @Override
    public BaseResponse requestTranslate(String content, String src, String dest) {
        return RequestManager.setRequestForRx(HttpRequestPool.getTranslateResult(content, src, dest));
//        return RequestManager.setRequestForRx(HttpRequestPool.mockLocation("http://adash.m.taobao.com/rest/sur?ak=21811227&av=3.0.0&c=10004852&v=3.0&s=e339dd7bf87a6cba609e56e9506108e3d02ae33f&d=V9%2B0VkEIMsUDAFPyuDC6WP7R&sv=4.4.1&p=a&t=1486435258768&u=&is=1"));
    }

//    /**
//     * Generate url that can be handled by Google API.
//     * formate to: q="This is a beautiful day!&target=es&format=text&source=en&key=" + APP_KEY;
//     *
//     * @param content string that need to be translated
//     * @return
//     */
//    private String paramToUrl(String content, String src, String dest) {
//        StringBuilder mParams = new StringBuilder();
//        mParams.append("q=").append(content).append("&");
//        mParams.append("target=").append(dest).append("&");
//        mParams.append("format=").append("text").append("&");
//        mParams.append("source=").append("en").append("&");
//        mParams.append("key=").append(ConstData.GOOTLE_APP_KEY);
//
//        return mParams.toString();
//    }
}
