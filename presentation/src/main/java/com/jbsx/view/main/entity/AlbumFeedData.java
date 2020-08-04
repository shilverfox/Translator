package com.jbsx.view.main.entity;

import java.util.List;

public class AlbumFeedData {
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
        private List<AlbumFeedItem> list;
        private int pageNum;
        private int pageSize;
        private int total;

        public List<AlbumFeedItem> getList() {
            return list;
        }

        public void setList(List<AlbumFeedItem> list) {
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

    public class AlbumFeedItem {
        private String classifyName;
        private String albumCode;
        private String albumName;
        private String albumPreview;
        private RepertoryData.MetaData metadata;

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getAlbumCode() {
            return albumCode;
        }

        public void setAlbumCode(String albumCode) {
            this.albumCode = albumCode;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public String getAlbumPreview() {
            return albumPreview;
        }

        public void setAlbumPreview(String albumPreview) {
            this.albumPreview = albumPreview;
        }

        public RepertoryData.MetaData getMetadata() {
            return metadata;
        }

        public void setMetadata(RepertoryData.MetaData metadata) {
            this.metadata = metadata;
        }
    }
}
