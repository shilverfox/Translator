package com.app.domain.net.repository;

import com.app.data.net.BaseRequestEntity;
import com.app.data.net.BaseResponse;

/**
 * Http request for testing
 *
 * Created by lijian15 on 2017/1/13.
 */

public class TaskDataSourceTestImpl implements TaskDataSource {
    @Override
    public BaseDominData requestTranslate(String content, String src, String dest) {
        return null;
    }

    @Override
    public BaseDominData getHotFixPatch() {
        return null;
    }
}
