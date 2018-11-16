package com.jbsx.view.myinfo.data;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

public class MyCommentData {
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
        private List<UserComments> userComments;
        private int totalPages;
        private int totalElements;
        private BaseDomainData.ResultStatus resultStatus;
        private List<Param> param;

        public List<UserComments> getUserComments() {
            return userComments;
        }

        public void setUserComments(List<UserComments> userComments) {
            this.userComments = userComments;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
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
        private String specialCode;
        private int type;
        private BaseDomainData.BasePage basePage;
        private BaseDomainData.Parameter parameter;

        public String getSpecialCode() {
            return specialCode;
        }

        public void setSpecialCode(String specialCode) {
            this.specialCode = specialCode;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public BaseDomainData.BasePage getBasePage() {
            return basePage;
        }

        public void setBasePage(BaseDomainData.BasePage basePage) {
            this.basePage = basePage;
        }

        public BaseDomainData.Parameter getParameter() {
            return parameter;
        }

        public void setParameter(BaseDomainData.Parameter parameter) {
            this.parameter = parameter;
        }
    }
}
