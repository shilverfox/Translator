package com.app.domain.net.repository;

import com.app.data.net.BaseRequestEntity;
import com.app.data.net.BaseResponse;

/**
 * Created by lijian15 on 2017/1/13.
 */

public interface TaskDataSource {
    BaseDominData requestTranslate(String content, String src, String dest);
    BaseDominData getHotFixPatch();
}
