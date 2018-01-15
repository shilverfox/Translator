package com.translatmaster.view.main.task;

import android.text.TextUtils;

import com.translatmaster.net.BaseResponse;

/**
 * Manage that how to get data from local or net.
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskManager {
    private TaskDataSource mTaskDataSource;

    public TaskManager(TaskDataSource dataSource) {
        mTaskDataSource = dataSource;
    }

    public BaseResponse requestTranslate(String content, String src, String dest) {
        if (mTaskDataSource != null && !TextUtils.isEmpty(content)) {
            return mTaskDataSource.requestTranslate(content, src, dest);
        }

        return null;
    }
}
