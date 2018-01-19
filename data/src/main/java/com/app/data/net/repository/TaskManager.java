package com.app.data.net.repository;

import com.app.domain.net.repository.ITaskDataSource;

/**
 * Manage that how to get data from local or net.
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskManager {
    private static ITaskDataSource mTaskDataSource = new TaskDataSourceImpl();

    public static ITaskDataSource getTaskManager() {
        return mTaskDataSource;
    }
}
