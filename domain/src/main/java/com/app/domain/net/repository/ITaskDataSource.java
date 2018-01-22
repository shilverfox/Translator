package com.app.domain.net.repository;

import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.BaseResponse;

/**
 * Created by lijian15 on 2017/1/13.
 */

public interface ITaskDataSource {
    BaseResponse handleRequest(BaseRequestEntity entity);
}
