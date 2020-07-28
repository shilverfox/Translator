package com.jbsx.view.main.entity;

public class VideoDetailData {
    private boolean status;
    private int code;
    private String msg;
    private RepertoryData.FeedItem body;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RepertoryData.FeedItem getBody() {
        return body;
    }

    public void setBody(RepertoryData.FeedItem body) {
        this.body = body;
    }
}
