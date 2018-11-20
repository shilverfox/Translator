package com.jbsx.player.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 视频播放需要的数据
 */
public class PlayerData implements Parcelable {
    /** 专题ID */
    private String albumId;

    /** 片库ID */
    private String singleId;

    /** type 1高清2标清 */
    private String type;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getSingleId() {
        return singleId;
    }

    public void setSingleId(String singleId) {
        this.singleId = singleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.albumId);
        dest.writeString(this.singleId);
        dest.writeString(this.type);
    }

    public PlayerData() {
    }

    protected PlayerData(Parcel in) {
        this.albumId = in.readString();
        this.singleId = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<PlayerData> CREATOR = new Parcelable.Creator<PlayerData>() {
        @Override
        public PlayerData createFromParcel(Parcel source) {
            return new PlayerData(source);
        }

        @Override
        public PlayerData[] newArray(int size) {
            return new PlayerData[size];
        }
    };
}
