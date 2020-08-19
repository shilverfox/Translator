package com.jbsx.view.main.entity;

public class OrgStateData {
    private boolean status;
    private int code;
    private String msg;
    private OrgStateInfo body;

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

    public OrgStateInfo getBody() {
        return body;
    }

    public void setBody(OrgStateInfo body) {
        this.body = body;
    }
}
