package com.jbsx.view.main.entity;

import java.util.List;

public class GalleryData {
    private boolean status;
    private int code;
    private String msg;
    private List<NavigationData.ClassifyEntity> body;

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

    public List<NavigationData.ClassifyEntity> getBody() {
        return body;
    }

    public void setBody(List<NavigationData.ClassifyEntity> body) {
        this.body = body;
    }
}
