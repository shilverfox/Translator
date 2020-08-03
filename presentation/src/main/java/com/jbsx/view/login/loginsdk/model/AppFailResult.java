package com.jbsx.view.login.loginsdk.model;

/**
 * Created by lijian15 on 2017/11/9.
 */

public class AppFailResult {
    private AppPicDataInfo picDataInfo;
    private AppJumpResult jumpResult;
    private byte replyCode;
    private String Message;
    private int intVal;

    public AppFailResult() {
    }

    public AppPicDataInfo getPicDataInfo() {
        return picDataInfo;
    }

    public void setPicDataInfo(AppPicDataInfo picDataInfo) {
        this.picDataInfo = picDataInfo;
    }

    public AppJumpResult getJumpResult() {
        return jumpResult;
    }

    public void setJumpResult(AppJumpResult jumpResult) {
        this.jumpResult = jumpResult;
    }

    public byte getReplyCode() {
        return replyCode;
    }

    public void setReplyCode(byte replyCode) {
        this.replyCode = replyCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }
}
