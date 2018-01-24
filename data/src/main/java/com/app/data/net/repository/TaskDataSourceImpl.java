package com.app.data.net.repository;

import com.app.domain.net.model.BaseRequestEntity;
import com.app.data.net.repository.http.HttpRequestHandler;
import com.app.domain.net.model.BaseResponse;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Http request
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskDataSourceImpl implements ITaskDataSource {
    @Override
    public BaseResponse handleRequest(BaseRequestEntity entity) {
        return HttpRequestHandler.setRequestForRx(entity);
    }
}
