package com.app.data.net.repository.local;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.model.BaseResponse;

/**
 * Make all the response by functionID
 *
 * Created by lijian15 on 2018/1/24.
 */

public class LocalResponseFactory {
    private static LocalResponseFactory mInstance;

    private LocalResponseFactory() {
    }

    public static LocalResponseFactory getInstance() {
        if (mInstance == null) {
            mInstance = new LocalResponseFactory();
        }

        return mInstance;
    }

    public BaseResponse getResponse(String functionId) {
        switch (functionId) {
            case ConstData.FUNCTION_ID_TEST:
                return getTestResponse();
            default:
                return getDefaultResponse();
        }
    }

    /**
     * Create a default response.
     *
     * @return
     */
    private BaseResponse getDefaultResponse() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent("getDefaultResponse");
        return baseResponse;
    }

    private BaseResponse getTestResponse() {
        BaseResponse baseResponse = new BaseResponse();
        String json = "{\n" +
                "    \"code\": \"0\",\n" +
                "    \"msg\": \"成功\",\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"id\": 12926,\n" +
                "            \"addressName\": \"addressName\",\n" +
                "            \"name\": \"name\",\n" +
                "            \"cityId\": 1,\n" +
                "            \"countyId\": 2810,\n" +
                "            \"cityName\": \"cityName\",\n" +
                "            \"countyName\": \"countyName\",\n" +
                "            \"poi\": \"poi\",\n" +
                "            \"addressDetail\": \"11\",\n" +
                "            \"fullAddress\": \"fullAddress\",\n" +
                "            \"mobile\": \"mobile\",\n" +
                "            \"coordType\": 2,\n" +
                "            \"longitude\": 116.51064,\n" +
                "            \"latitude\": 39.79553,\n" +
                "            \"addressType\": 1,\n" +
                "            \"pin\": \"pin\",\n" +
                "            \"lastUsedTime\": 1495070862719,\n" +
                "            \"createTime\": \"2016-12-30 17:12:52\",\n" +
                "            \"createPin\": \"createPin\",\n" +
                "            \"updateTime\": \"2017-05-18 09:27:42\",\n" +
                "            \"updatePin\": \"updatePin\",\n" +
                "            \"yn\": 0,\n" +
                "            \"ts\": \"2017-05-18 09:27:42\",\n" +
                "            \"quantityOfGoods\": 0,\n" +
                "            \"exists\": false,\n" +
                "            \"needCheckDelivery\": 0,\n" +
                "            \"canDelivery\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 13919,\n" +
                "            \"groupName\": \"\",\n" +
                "            \"addressName\": \"1\",\n" +
                "            \"name\": \"2\",\n" +
                "            \"cityId\": 1,\n" +
                "            \"countyId\": 2810,\n" +
                "            \"cityName\": \"3\",\n" +
                "            \"countyName\": \"4\",\n" +
                "            \"poi\": \"5\",\n" +
                "            \"addressDetail\": \"6\",\n" +
                "            \"fullAddress\": \"7\",\n" +
                "            \"mobile\": \"153****9602\",\n" +
                "            \"coordType\": 1,\n" +
                "            \"longitude\": 116.5049,\n" +
                "            \"latitude\": 39.795002,\n" +
                "            \"addressType\": 1,\n" +
                "            \"pin\": \"a\",\n" +
                "            \"lastUsedTime\": 1492760315759,\n" +
                "            \"createTime\": \"2017-03-03 10:27:12\",\n" +
                "            \"createPin\": \"J\",\n" +
                "            \"updateTime\": \"2017-04-21 15:38:35\",\n" +
                "            \"updatePin\": \"Jjjjj\",\n" +
                "            \"yn\": 0,\n" +
                "            \"ts\": \"2017-04-21 15:38:35\",\n" +
                "            \"quantityOfGoods\": 0,\n" +
                "            \"exists\": false,\n" +
                "            \"needCheckDelivery\": 0,\n" +
                "            \"canDelivery\": true\n" +
                "        }\n" +
                "    ],\n" +
                "    \"success\": true\n" +
                "}";
        baseResponse.setContent(json);

        return baseResponse;
    }
}
