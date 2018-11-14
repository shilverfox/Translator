package com.jbsx.utils;

import android.text.TextUtils;

import com.app.domain.net.model.BaseDomainData;

public class MessageTools {
    public final static String DEFAULT_ERROR_MESSAGE = "好像哪里不对";

    public static void showErrorMessage(BaseDomainData data) {
        if (hasErrorMessage(data)) {
            String error = data.getPayload().getResultStatus().getMessage();
            ShowTools.toast(error);
        }
    }

    public static boolean hasErrorMessage(BaseDomainData data) {
        return data != null && data.getPayload() != null && data.getPayload().getResultStatus() != null
                && !TextUtils.isEmpty(data.getPayload().getResultStatus().getMessage());
    }

    public static String getMessage(BaseDomainData data) {
        if (hasErrorMessage(data)) {
            return data.getPayload().getResultStatus().getMessage();
        } else {
            return "";
        }
    }
}
