package com.jbsx.weixin;

/**
 * Created by wyy on 2017/8/10.
 */

public class WXShareHelper {
    private static WXShareHelper instance = new WXShareHelper();
    private OnWXShareSuccessListener onWXShareSuccessListener;

    public static WXShareHelper getInstance() {
        return instance;
    }

    public void handleResOfShareSuccess(){
        if(onWXShareSuccessListener!=null){
            onWXShareSuccessListener.onSuccess();
        }
    }

    public interface OnWXShareSuccessListener{
        void onSuccess();
    }

    void addOnWXShareSuccessListener(OnWXShareSuccessListener onWXShareSuccessListener){
        this.onWXShareSuccessListener=onWXShareSuccessListener;
    }

}
