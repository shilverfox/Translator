package com.jbsx.view.main.entity;

import java.util.List;

public class LocalPictureFeedData {
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
        private List<FeedItem> atlasList;
        private List<String> classifyList;
        private int pageNum;
        private int pageSize;
        private int total;

        public List<FeedItem> getAtlasList() {
            return atlasList;
        }

        public void setAtlasList(List<FeedItem> atlasList) {
            this.atlasList = atlasList;
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
        private String atlasCode;
        private String atlasPreview;
        private String atlasTitle;
        private String localClassify;
        private String orgCode;

        public String getAtlasCode() {
            return atlasCode;
        }

        public void setAtlasCode(String atlasCode) {
            this.atlasCode = atlasCode;
        }

        public String getAtlasPreview() {
            return atlasPreview;
        }

        public void setAtlasPreview(String atlasPreview) {
            this.atlasPreview = atlasPreview;
        }

        public String getAtlasTitle() {
            return atlasTitle;
        }

        public void setAtlasTitle(String atlasTitle) {
            this.atlasTitle = atlasTitle;
        }

        public String getLocalClassify() {
            return localClassify;
        }

        public void setLocalClassify(String localClassify) {
            this.localClassify = localClassify;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }
    }
}
