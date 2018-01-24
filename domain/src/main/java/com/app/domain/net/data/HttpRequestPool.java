package com.app.domain.net.data;

import com.app.domain.net.model.BaseBody;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.RequestConst;

/**
 * List all the http request.
 *
 * Created by lijian15 on 2016/12/14.
 */

public class HttpRequestPool {

    /**
     * Generate url that can be handled by Google API.
     * formate to: q="This is a beautiful day!&target=es&format=text&source=en&key=" + APP_KEY;
     *
     * @param content string that need to be translated
     * @return
     */
    public static BaseRequestEntity getTranslateResult(String content, String src, String dest) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.GOOGLE_TRANS_URL);

        BaseBody body = new BaseBody();
        body.add("q", content);
        body.add("target", dest);
        body.add("format", "text");
        body.add("source", "en");
        body.add("key", ConstData.GOOGLE_APP_KEY);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getTestEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl("http://testgw.o2o.jd.com/client?");

        BaseBody body = new BaseBody();
        body.add("city_id", "1930");
        body.add("deviceId", "5d56134c146fc3b6923c2e66dd774175");
        body.add("networkType", "WIFI");
        body.add("screen", "2392*1440");
        body.add("appName", "Paidaojia");
        body.add("signKey", "e30d48a05849463873b87f709a534134");
        body.add(ConstData.KEY_FUNCTION_ID, ConstData.FUNCTION_ID_TEST);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * Download the patch
     *
     * @return
     */
    public static BaseRequestEntity getHotFixPatch() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOT_PATCH_URL);
        baseRequest.setmNeedByteData(true);

        return baseRequest;
    }

    /**
     * Just for testing
     *
     * @param url
     * @return
     */
    public static BaseRequestEntity mockLocation(String url) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(url);
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("ak", "21811227");
        body.add("av", "3.0.0");
        body.add("c", "10004852");
        body.add("s", "e339dd7bf87a6cba609e56e9506108e3d02ae33f");
        body.add("d", "V9%2B0VkEIMsUDAFPyuDC6WP7R");
        body.add("sv", "4.4.1");
        body.add("p", "a");
        body.add("t", "1486435258768");
        body.add("is", "1");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }
}
