package com.app.data.net.repository.local;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.model.BaseBody;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.BaseResponse;

import java.util.HashMap;

/**
 * Return static json string.
 * just for testing.
 *
 * Created by lijian15 on 2018/1/24.
 */

public class LocalRequestHandler {
    public static BaseResponse setRequestForLocal(BaseRequestEntity appRequest) {
        return LocalResponseFactory.getInstance().getResponse(getFunctionId(appRequest));
    }

    private static String getFunctionId(BaseRequestEntity appRequest) {
        String functionId = "";
        BaseBody baseBody = appRequest.getBaseBody();
        if (baseBody != null) {
            HashMap<String, Object> keyAndValue = baseBody.getMapBody();
            functionId = (String)keyAndValue.get(ConstData.KEY_FUNCTION_ID);
        }

        return functionId;
    }
}
