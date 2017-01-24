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
        String url = ConstData.GOOGLE_TRANS_URL + paramToUrl(content, src, dest);

        return RequestManager.setRequestForRx(HttpRequestPool.getTranslateResult(url));
    }

    /**
     * Generate url that can be handled by Google API.
     * formate to: q="This is a beautiful day!&target=es&format=text&source=en&key=" + APP_KEY;
     *
     * @param content string that need to be translated
     * @return
     */
    private String paramToUrl(String content, String src, String dest) {
        StringBuilder mParams = new StringBuilder();
        mParams.append("q=").append(content).append("&");
        mParams.append("target=").append(dest).append("&");
        mParams.append("format=").append("text").append("&");
        mParams.append("source=").append("en").append("&");
        mParams.append("key=").append(ConstData.GOOTLE_APP_KEY);

        return mParams.toString();
    }
}
