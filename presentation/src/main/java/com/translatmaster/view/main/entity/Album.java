package com.translatmaster.view.main.entity;

public class Album {
    private String id;
    private String title;
    private String introduce;
    private String appImageUrl;
    private String pcImageUrl;
    private String generalizeImageUrl;
    private int songs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGeneralizeImageUrl() {
        return generalizeImageUrl;
    }

    public void setGeneralizeImageUrl(String generalizeImageUrl) {
        this.generalizeImageUrl = generalizeImageUrl;
    }

    public int getSongs() {
        return songs;
    }

    public void setSongs(int songs) {
        this.songs = songs;
    }
}
