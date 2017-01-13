package com.translatmaster.view.main.task;

import com.translatmaster.net.BaseResponse;

/**
 * Created by lijian15 on 2017/1/13.
 */

public interface TaskDataSource {
    BaseResponse requestTranslate(String content);
}
