package com.jbsx.view.main.entity;

import java.util.List;

public class SearchResultData {
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
        private List<SearchResultItem> list;
        private int pageNum;
        private int pageSize;
        private int total;

        public List<SearchResultItem> getList() {
            return list;
        }

        public void setList(List<SearchResultItem> list) {
            this.list = list;
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

    public class SearchResultItem {
        private String resourceName;
        private String resourceCode;
        private int resourceType;
        private String resourcePreview;
        private RepertoryData.MetaData meta;

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public String getResourceCode() {
            return resourceCode;
        }

        public void setResourceCode(String resourceCode) {
            this.resourceCode = resourceCode;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }

        public String getResourcePreview() {
            return resourcePreview;
        }

        public void setResourcePreview(String resourcePreview) {
            this.resourcePreview = resourcePreview;
        }

        public RepertoryData.MetaData getMetadata() {
            return meta;
        }

        public void setMetadata(RepertoryData.MetaData metadata) {
            this.meta = metadata;
        }
    }
}
