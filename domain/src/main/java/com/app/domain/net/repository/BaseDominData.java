package com.app.domain.net.repository;

import com.app.data.net.BaseResponse;

/**
 * Wrap the data of data layer so as to send to presention layer
 *
 * Created by lijian15 on 2018/1/16.
 */

public class BaseDominData {
    private BaseResponse mBaseResponse;

    public BaseResponse getBaseResponse() {
        return mBaseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        mBaseResponse = baseResponse;
    }
}
