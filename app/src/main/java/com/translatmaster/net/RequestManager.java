package com.translatmaster.net;

import android.text.TextUtils;

import com.translatmaster.data.ConstData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Handle http request
 *
 * Created by lijian15 on 2016/12/14.
 */

public class RequestManager {
    public static void setRequest(BaseRequestEntity appRequest,
                                  final IBaseRequestCallback appBaseCallback) {

        if (appRequest != null && !TextUtils.isEmpty(appRequest.getUrl())) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(appRequest.getUrl()).get().build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (appBaseCallback != null) {
                        appBaseCallback.onRequestFailed("");
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (appBaseCallback != null) {
                        if (response != null && response.isSuccessful()) {
                            appBaseCallback.onRequestSuccessful(response.body().string());
                        } else {
                            // If json is null, handled by onFailure
                            appBaseCallback.onRequestFailed("");
                        }
                    }
                }
            });
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

        if (appRequest != null && !TextUtils.isEmpty(appRequest.getUrl())) {
            Response response = null;
            OkHttpClient okHttpClient = new OkHttpClient();


            BaseBody baseBody = appRequest.getBaseBody();
            Request.Builder builder =  new Request.Builder();
            if (appRequest.getMethod() == RequestConst.REQUEST_POST) {
                // Http post
                FormBody.Builder bodyBuilder = new FormBody.Builder();

                if (baseBody != null) {
                    HashMap<String, String> bodys = baseBody.getMapBody();
                    if (bodys != null) {
                        Iterator<Map.Entry<String, String>> it = bodys.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, String> entry = it.next();
                            bodyBuilder.add(entry.getKey(), entry.getValue());
                        }
                    }

                    RequestBody formBody = bodyBuilder.build();

                    builder.url(appRequest.getUrl());
                    builder.post(formBody);
                }
            } else {
                // Http get
                if (baseBody != null) {
                    builder.url(appRequest.getUrl() + baseBody.getStringBody());
                }
                builder.get();
            }

            Request request = builder.build();

            try {
                response = okHttpClient.newCall(request).execute();

                if (response != null && response.isSuccessful()) {
                    baseResponse.setSuccessful(true);
                    baseResponse.setContent(response.body().string());
                } else {
                    baseResponse.setSuccessful(true);
                    baseResponse.setContent(response.body().string());
                }
            } catch (IOException e) {
                baseResponse.setSuccessful(false);
                baseResponse.setContent(ConstData.DEFAULT_NET_ERROR);
            }
        } else {
            baseResponse.setSuccessful(false);
            baseResponse.setContent(ConstData.DEFAULT_NET_ERROR);
        }

        return baseResponse;
    }
}
