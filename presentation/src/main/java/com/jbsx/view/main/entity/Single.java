package com.jbsx.view.main.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Single implements Parcelable {
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
    private int type; //1片库 2专辑
    private boolean isCheck;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.specialAlbumId);
        dest.writeString(this.title);
        dest.writeString(this.introduce);
        dest.writeString(this.appImageUrl);
        dest.writeString(this.pcImageUrl);
        dest.writeString(this.nextId);
        dest.writeInt(this.playNum);
        dest.writeString(this.isCollect);
        dest.writeString(this.longTime);
        dest.writeInt(this.second);
        dest.writeInt(this.type);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }

    public Single() {
    }

    protected Single(Parcel in) {
        this.id = in.readString();
        this.specialAlbumId = in.readString();
        this.title = in.readString();
        this.introduce = in.readString();
        this.appImageUrl = in.readString();
        this.pcImageUrl = in.readString();
        this.nextId = in.readString();
        this.playNum = in.readInt();
        this.isCollect = in.readString();
        this.longTime = in.readString();
        this.second = in.readInt();
        this.type = in.readInt();
        this.isCheck = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Single> CREATOR = new Parcelable.Creator<Single>() {
        @Override
        public Single createFromParcel(Parcel source) {
            return new Single(source);
        }

        @Override
        public Single[] newArray(int size) {
            return new Single[size];
        }
    };
}
