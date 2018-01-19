package com.app.domain.net;

import com.app.domain.net.model.BaseDomainData;

/**
 * Callback for http request
 *
 * Created by lijian15 on 2016/12/14.
 */

public interface IBaseRequestCallback<T> {
    /**
     * Failed
     *
     * @param data
     */
    void onRequestFailed(T data);

    /**
     * Successful
     *
     * @param data
     */
    void onRequestSuccessful(T data);
}
