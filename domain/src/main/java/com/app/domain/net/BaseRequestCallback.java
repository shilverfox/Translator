package com.app.domain.net;

import com.app.domain.net.model.BaseDomainData;

/**
 * Callback for http request
 *
 * Created by lijian15 on 2016/12/14.
 */

public abstract class BaseRequestCallback {
    /**
     * Failed
     *
     * @param data
     */
    public abstract void onRequestFailed(BaseDomainData data);

    /**
     * Successful
     *
     * @param data
     */
    public abstract void onRequestSuccessful(String data);

//    /**
//     * 获取Json的类型，因为数据可能集合也可能是Object *
//     *
//     * @return
//     */
//    public Type getType() {
//        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        if(type instanceof Class){
//            return type;//如果是Object直接返回
//        }else{
//            return new TypeToken<T>(){}.getType();//如果是集合，获取集合的类型map或list
//        }
//    }
}
