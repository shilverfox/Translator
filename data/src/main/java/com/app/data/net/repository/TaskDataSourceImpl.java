package com.app.data.net.repository;

import com.app.data.net.data.HttpRequestPool;
import com.app.data.net.model.BaseRequestEntity;
import com.app.data.net.model.BaseResponse;
import com.app.data.net.RequestManager;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Http request
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskDataSourceImpl implements ITaskDataSource {
    @Override
    public String requestTranslate(String content, String src, String dest) {
//        BaseRequestEntity entity = HttpRequestPool.getTranslateResult(content, src, dest);
        BaseRequestEntity entity = HttpRequestPool.getTestEntity();
        return RequestManager.setRequestForRx(entity).getContent();
    }

    @Override
    public String getHotFixPatch() {
        return RequestManager.setRequestForRx(HttpRequestPool.getHotFixPatch()).getContent();
    }

    private void handleBaseResponse(BaseResponse baseResponse) {
        if (baseResponse != null) {
            String content = baseResponse.getContent();
        }
    }
}
