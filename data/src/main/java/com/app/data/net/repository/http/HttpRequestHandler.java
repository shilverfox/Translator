package com.app.data.net.repository.http;

import android.text.TextUtils;

import com.app.domain.net.model.BaseBody;
import com.app.domain.net.model.BaseHeader;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.BaseResponse;
import com.app.domain.net.model.RequestConst;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Handle http request
 *
 * Created by lijian15 on 2016/12/14.
 */

public class HttpRequestHandler {
    public final static String DEFAULT_NET_ERROR = "NET ERROR";

//    public static void setRequest(BaseRequestEntity appRequest,
//                                  final BaseRequestCallback appBaseCallback) {
//
//        if (appRequest != null && !TextUtils.isEmpty(appRequest.getUrl())) {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            Request request = new Request.Builder().url(appRequest.getUrl()).get().build();
//
//            okHttpClient.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    if (appBaseCallback != null) {
//                        appBaseCallback.onRequestFailed("");
//                    }
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (appBaseCallback != null) {
//                        if (response != null && response.isSuccessful()) {
//                            appBaseCallback.onRequestSuccessful(response.body().string());
//                        } else {
//                            // If json is null, handled by onFailure
//                            appBaseCallback.onRequestFailed("");
//                        }
//                    }
//                }
//            });
//        }
//    }

    private static Request.Builder handleGetPostRequestBuilder(String baseUrl, BaseBody baseBody) {
        // 是否需要上传文件
        boolean hasFileToUpload = (baseBody != null && baseBody.getFile() != null);

        if (hasFileToUpload) {
            return getPostRequestBuilderForUpload(baseUrl, baseBody);
        } else {
            return getPostRequestBuilderByJson(baseUrl, baseBody);
        }
    }

    /**
     * 按Json传数据
     *
     * @param baseUrl
     * @param baseBody
     * @return
     */
    private static Request.Builder getPostRequestBuilderByJson(String baseUrl, BaseBody baseBody) {
        Request.Builder builder =  new Request.Builder();

        if (baseBody != null && !TextUtils.isEmpty(baseUrl)) {
            Gson gson = new Gson();
            String json = gson.toJson(baseBody.getMapBody());
            // MediaType  设置Content-Type 标头中包含的媒体类型值
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            builder.url(baseUrl);
            builder.post(requestBody);
        }

        return builder;
    }

    /**
     * 文件上传
     *
     * @param baseUrl
     * @param baseBody
     * @return
     */
    private static Request.Builder getPostRequestBuilderForUpload(String baseUrl, BaseBody baseBody) {
        Request.Builder builder =  new Request.Builder();

        if (baseBody != null && !TextUtils.isEmpty(baseUrl)) {
            MultipartBody.Builder mulBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", baseBody.getFileName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), baseBody.getFile()));

            HashMap<String, Object> bodys = baseBody.getMapBody();
            if (bodys != null) {
                Iterator<Map.Entry<String, Object>> it = bodys.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> entry = it.next();
                    mulBuilder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
            }

            RequestBody requestBody = mulBuilder.build();
            builder.url(baseUrl);
            builder.post(requestBody);
        }

        return builder;
    }

    /**
     * 按字符串传，外部没限制加入body的类型，所以此方法实际调用可能会产生异常，权当是留个备份，别瞎jb用
     * @deprecated
     *
     * @param baseUrl
     * @param baseBody
     * @return
     */
    private static Request.Builder getPostRequestBuilderByString(String baseUrl, BaseBody baseBody) {
        Request.Builder builder =  new Request.Builder();

        if (baseBody != null && !TextUtils.isEmpty(baseUrl)) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();

            HashMap<String, Object> bodys = baseBody.getMapBody();
            if (bodys != null) {
                Iterator<Map.Entry<String, Object>> it = bodys.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> entry = it.next();
                    bodyBuilder.add(entry.getKey(), entry.getValue().toString());
                }
            }

            RequestBody formBody = bodyBuilder.build();

            builder.url(baseUrl);
            builder.post(formBody);
        }

        return builder;
    }

    private static Request.Builder getGetRequestBuilder(String baseUrl, BaseBody baseBody) {
        Request.Builder builder =  new Request.Builder();

        if (!TextUtils.isEmpty(baseUrl)) {
            if (baseBody != null) {
                builder.url(baseUrl + baseBody.getStringBody());
            } else {
                builder.url(baseUrl);
            }

            builder.get();
        }

        return builder;
    }

    private static void handleHttpRequest(Request request, BaseResponse baseResponse, boolean needByteData) {
        Response response = null;
        OkHttpClient okHttpClient = new OkHttpClient();

        try {
            response = okHttpClient.newCall(request).execute();

            if (response != null && response.isSuccessful()) {
                handleSuccessful(response, baseResponse, needByteData);
            } else {
                handleDataError(baseResponse);
            }
        } catch (IOException e) {
            handleNetError(baseResponse);
        }
    }

    private static void handleSuccessful(Response response, BaseResponse baseResponse, boolean needByteData) {
        if (response != null && baseResponse != null) {
            // Save content as String or byte[]
            try {
                if (needByteData) {
                    baseResponse.setByteData(response.body().bytes());
                } else {
                    baseResponse.setContent(response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
                handleDataError(baseResponse);
            }
        }
    }

    private static void handleNetError(BaseResponse baseResponse) {
        if (baseResponse != null) {
            baseResponse.setContent(DEFAULT_NET_ERROR);
        }
    }

    private static void handleDataError(BaseResponse baseResponse) {
        if (baseResponse != null) {
            baseResponse.setContent(DEFAULT_NET_ERROR);
        }
    }

    /**
     * Using for RxJava
     *
     * @param appRequest
     * @return
     */
    public static BaseResponse setRequestForRx(BaseRequestEntity appRequest) {
        BaseResponse baseResponse = new BaseResponse();
        String baseUrl = null;
        String functionId = "";

        if (appRequest != null && !TextUtils.isEmpty(appRequest.getUrl())) {
            Request.Builder builder = null;
            baseUrl = appRequest.getUrl();
            functionId = appRequest.getFunctionId();
            baseUrl += TextUtils.isEmpty(functionId) ? "" : functionId;

            if (appRequest.getMethod() == RequestConst.REQUEST_POST) {
                // Http post
                builder = handleGetPostRequestBuilder(baseUrl, appRequest.getBaseBody());
            } else {
                // Http get
                builder = getGetRequestBuilder(baseUrl, appRequest.getBaseBody());
            }

            // header
            BaseHeader header = appRequest.getBaseHeader();
            if (header != null) {
                HashMap<String, String>  headerMap = header.getMapHeader();

                Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            handleHttpRequest(builder.build(), baseResponse, appRequest.needByteData());
        }

        return baseResponse;
    }
}
