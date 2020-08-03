package com.jbsx.data;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

/**
 * Created by lijian on 2018/12/27.
 */

public class RequestTokenData {
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

        private String token;
        private String key;
        private String imagePreUrl;
        private BaseDomainData.ResultStatus resultStatus;
        private List<Param> param;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getImagePreUrl() {
            return imagePreUrl;
        }

        public void setImagePreUrl(String imagePreUrl) {
            this.imagePreUrl = imagePreUrl;
        }

        public BaseDomainData.ResultStatus getResultStatus() {
            return resultStatus;
        }

        public void setResultStatus(BaseDomainData.ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
        }

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public class Param {

        private String userId;
        private int fileType;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public BaseDomainData.Parameter getParameter() {
            return parameter;
        }

        public void setParameter(BaseDomainData.Parameter parameter) {
            this.parameter = parameter;
        }

        private BaseDomainData.Parameter parameter;


    }
}
