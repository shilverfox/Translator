package com.translatmaster.view.main.entity;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

/**
 * 首页主讲嘉宾
 *
 * Created by lijian on 2018/11/10.
 */

public class HostData {
    private boolean status;
    private int code;
    private String msg;
    private Payload payload;

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

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public class Payload {
        private List<Celebrities> celebrities;
        private BaseDomainData.ResultStatus resultStatus;

        public List<Celebrities> getCelebrities() {
            return celebrities;
        }

        public void setCelebrities(List<Celebrities> celebrities) {
            this.celebrities = celebrities;
        }

        public BaseDomainData.ResultStatus getResultStatus() {
            return resultStatus;
        }

        public void setResultStatus(BaseDomainData.ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
        }
    }
}
