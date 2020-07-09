package com.jbsx.view.main.entity;

import java.util.List;

public class MainPageData {
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
        private List<HomeNewsEntity> newsList;
        private List<HomeRecommendEntity> recommends;

        public List<HomeNewsEntity> getNewsList() {
            return newsList;
        }

        public void setNewsList(List<HomeNewsEntity> newsList) {
            this.newsList = newsList;
        }

        public List<HomeRecommendEntity> getRecommends() {
            return recommends;
        }

        public void setRecommends(List<HomeRecommendEntity> recommends) {
            this.recommends = recommends;
        }
    }

    public class HomeNewsEntity {
        private String editor;
        private String localClassify;
        private String newsBanner;
        private String newsCode;
        private String newsContent;
        private String newsTime;
        private String newsTitle;
        private String orgCode;
        private int publishStatus;
        private String source;

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getLocalClassify() {
            return localClassify;
        }

        public void setLocalClassify(String localClassify) {
            this.localClassify = localClassify;
        }

        public String getNewsBanner() {
            return newsBanner;
        }

        public void setNewsBanner(String newsBanner) {
            this.newsBanner = newsBanner;
        }

        public String getNewsCode() {
            return newsCode;
        }

        public void setNewsCode(String newsCode) {
            this.newsCode = newsCode;
        }

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }

        public String getNewsTime() {
            return newsTime;
        }

        public void setNewsTime(String newsTime) {
            this.newsTime = newsTime;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    public class HomeRecommendEntity {
        private String preview;
        private String resourceCode;
        private String resourceName;
        private int resourceType;

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public String getResourceCode() {
            return resourceCode;
        }

        public void setResourceCode(String resourceCode) {
            this.resourceCode = resourceCode;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }
    }
}
