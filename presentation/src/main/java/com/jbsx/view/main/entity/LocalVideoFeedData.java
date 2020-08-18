package com.jbsx.view.main.entity;

import java.util.List;

public class LocalVideoFeedData {
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
        private List<FeedItem> localVideoList;
        private List<String> classifyList;
        private int pageNum;
        private int pageSize;
        private int total;

        public List<FeedItem> getLocalVideoList() {
            return localVideoList;
        }

        public void setLocalVideoList(List<FeedItem> localVideoList) {
            this.localVideoList = localVideoList;
        }

        public List<String> getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(List<String> classifyList) {
            this.classifyList = classifyList;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public class FeedItem {
        private String filePath;
        private String localClassify;
        private String localVideoCode;
        private String localVideoName;
        private String localVideoPreview;
        private String orgCode;
        private int publishStatus;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getLocalClassify() {
            return localClassify;
        }

        public void setLocalClassify(String localClassify) {
            this.localClassify = localClassify;
        }

        public String getLocalVideoCode() {
            return localVideoCode;
        }

        public void setLocalVideoCode(String localVideoCode) {
            this.localVideoCode = localVideoCode;
        }

        public String getLocalVideoName() {
            return localVideoName;
        }

        public void setLocalVideoName(String localVideoName) {
            this.localVideoName = localVideoName;
        }

        public String getLocalVideoPreview() {
            return localVideoPreview;
        }

        public void setLocalVideoPreview(String localVideoPreview) {
            this.localVideoPreview = localVideoPreview;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public int getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(int publishStatus) {
            this.publishStatus = publishStatus;
        }
    }
}
