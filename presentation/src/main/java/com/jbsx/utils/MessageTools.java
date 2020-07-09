package com.jbsx.utils;

import android.text.TextUtils;

import com.app.domain.net.model.BaseDomainData;

public class MessageTools {
    public final static String DEFAULT_ERROR_MESSAGE = "好像哪里不对";

    public static void showErrorMessage(BaseDomainData data) {
        if (hasErrorMessage(data)) {
            String error = data.getMsg();
            ShowTools.toast(error);
        }
    }

    public static BaseDomainData createNoNetMessage() {
        BaseDomainData data = new BaseDomainData();
        data.setMsg(ErroBarHelper.ERRO_TYPE_NET_INTERNET);

        return data;
    }

    public static boolean hasErrorMessage(BaseDomainData data) {
        return data != null && !TextUtils.isEmpty(data.getMsg());
    }

    public static String getMessage(BaseDomainData data) {
        if (hasErrorMessage(data)) {
            return data.getMsg();
        } else {
            return "";
        }
    }
}
