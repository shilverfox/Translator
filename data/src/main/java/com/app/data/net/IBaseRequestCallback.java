package com.app.data.net;

/**
 * Callback for http request
 *
 * Created by lijian15 on 2016/12/14.
 */

public interface IBaseRequestCallback {
    /**
     * Failed
     *
     * @param errMessage
     */
    void onRequestFailed(String errMessage);

    /**
     * Successful
     *
     * @param json
     */
    void onRequestSuccessful(String json);
}
