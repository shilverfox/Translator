package com.jbsx.view.main.entity;

import java.util.List;

public class RepertoryData {
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
        private List<FeedItem> list;
        private int pageNum;
        private int pageSize;
        private int total;

        public List<FeedItem> getList() {
            return list;
        }

        public void setList(List<FeedItem> list) {
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

    public class FeedItem {
        private String classifyName;
        private String videoCode;
        private String videoFilePath;
        private String videoName;
        private String videoPreview;
        private MetaData metadata;

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getVideoCode() {
            return videoCode;
        }

        public void setVideoCode(String videoCode) {
            this.videoCode = videoCode;
        }

        public String getVideoFilePath() {
            return videoFilePath;
        }

        public void setVideoFilePath(String videoFilePath) {
            this.videoFilePath = videoFilePath;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getVideoPreview() {
            return videoPreview;
        }

        public void setVideoPreview(String videoPreview) {
            this.videoPreview = videoPreview;
        }

        public MetaData getMetadata() {
            return metadata;
        }

        public void setMetadata(MetaData metadata) {
            this.metadata = metadata;
        }
    }

    public class MetaData {
        private String actor;
        private String albumType;
        private String director;
        private String intro;
        private String playDate;
        private String videoTime;

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getAlbumType() {
            return albumType;
        }

        public void setAlbumType(String albumType) {
            this.albumType = albumType;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPlayDate() {
            return playDate;
        }

        public void setPlayDate(String playDate) {
            this.playDate = playDate;
        }

        public String getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(String videoTime) {
            this.videoTime = videoTime;
        }
    }
}
