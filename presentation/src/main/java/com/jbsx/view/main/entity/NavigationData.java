package com.jbsx.view.main.entity;

import java.util.List;

public class NavigationData {
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
        private List<ClassifyEntity> classifyList;
        private String deviceCode;
        private String orgCode;
        private String orgLogo;
        private String orgName;

        public List<ClassifyEntity> getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(List<ClassifyEntity> classifyList) {
            this.classifyList = classifyList;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOrgLogo() {
            return orgLogo;
        }

        public void setOrgLogo(String orgLogo) {
            this.orgLogo = orgLogo;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }

    public class ClassifyEntity {
        private String classifyCode;
        private String classifyDesc;
        private String classifyName;
        private String classifyOrder;
        private String classifyPCode;
        private String classifyPreview;
        private int classifyType;
        private boolean hasChildren;

        public boolean isHasChildren() {
            return hasChildren;
        }

        public void setHasChildren(boolean hasChildren) {
            this.hasChildren = hasChildren;
        }

        public String getClassifyCode() {
            return classifyCode;
        }

        public void setClassifyCode(String classifyCode) {
            this.classifyCode = classifyCode;
        }

        public String getClassifyDesc() {
            return classifyDesc;
        }

        public void setClassifyDesc(String classifyDesc) {
            this.classifyDesc = classifyDesc;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getClassifyOrder() {
            return classifyOrder;
        }

        public void setClassifyOrder(String classifyOrder) {
            this.classifyOrder = classifyOrder;
        }

        public String getClassifyPCode() {
            return classifyPCode;
        }

        public void setClassifyPCode(String classifyPCode) {
            this.classifyPCode = classifyPCode;
        }

        public String getClassifyPreview() {
            return classifyPreview;
        }

        public void setClassifyPreview(String classifyPreview) {
            this.classifyPreview = classifyPreview;
        }

        public int getClassifyType() {
            return classifyType;
        }

        public void setClassifyType(int classifyType) {
            this.classifyType = classifyType;
        }
    }
}
