package com.app.data.net.repository;

import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.BaseResponse;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Http request for testing
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskDataSourceTestImpl implements ITaskDataSource {
    @Override
    public BaseResponse handleRequest(BaseRequestEntity entity) {
        return null;
    }
}
