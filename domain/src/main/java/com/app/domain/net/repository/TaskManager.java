package com.app.domain.net.repository;

import com.app.data.net.BaseRequestEntity;
import com.app.data.net.BaseResponse;

/**
 * Manage that how to get data from local or net.
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskManager {
    private static TaskDataSource mTaskDataSource = new TaskDataSourceImpl();

    public static TaskDataSource getTaskManager() {
        return mTaskDataSource;
    }
}
