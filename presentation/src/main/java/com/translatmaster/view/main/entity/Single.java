package com.translatmaster.view.main.entity;

public class Single {
    private String id;
    private String specialAlbumId;
    private String title;
    private String introduce;
    private String appImageUrl;
    private String pcImageUrl;
    private String nextId;
    private int playNum;
    private String isCollect;
    private String longTime;
    private int second;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecialAlbumId() {
        return specialAlbumId;
    }

    public void setSpecialAlbumId(String specialAlbumId) {
        this.specialAlbumId = specialAlbumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAppImageUrl() {
        return appImageUrl;
    }

    public void setAppImageUrl(String appImageUrl) {
        this.appImageUrl = appImageUrl;
    }

    public String getPcImageUrl() {
        return pcImageUrl;
    }

    public void setPcImageUrl(String pcImageUrl) {
        this.pcImageUrl = pcImageUrl;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
