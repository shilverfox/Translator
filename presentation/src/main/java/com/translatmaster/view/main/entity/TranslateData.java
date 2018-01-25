package com.translatmaster.view.main.entity;

import java.util.List;

/**
 * Created by lijian15 on 2018/1/25.
 */

public class TranslateData {
    private String code;
    private String msg;
    private boolean success;
    private List<TranslateResult> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResult(List<TranslateResult> result) {
        this.result = result;
    }
}
