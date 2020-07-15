package com.jbsx.view.main.entity;

import java.util.List;

public class GalleryData {
    private boolean status;
    private int code;
    private String msg;
    private Result body;

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

    public Result getBody() {
        return body;
    }

    public void setBody(Result body) {
        this.body = body;
    }

    public class Result {
        private List<NavigationData.ClassifyEntity> classifyList;

        public List<NavigationData.ClassifyEntity> getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(List<NavigationData.ClassifyEntity> classifyList) {
            this.classifyList = classifyList;
        }
    }
}
