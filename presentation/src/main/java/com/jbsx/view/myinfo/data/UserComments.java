package com.jbsx.view.myinfo.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.jbsx.view.main.entity.Single;

public class UserComments implements Parcelable {
    private String id;
    private String userId;
    private String account;
    private String userImg;
    private String comment;
    private int commentLove;
    private int commentReply;
    private String specialAlbumId;
    private String specialSingleId;
    private String specialSingleTitle;
    private String createdAt;
    private String isLove;
    private String userImage;
    private Single single;

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCommentLove() {
        return commentLove;
    }

    public void setCommentLove(int commentLove) {
        this.commentLove = commentLove;
    }

    public int getCommentReply() {
        return commentReply;
    }

    public void setCommentReply(int commentReply) {
        this.commentReply = commentReply;
    }

    public String getSpecialAlbumId() {
        return specialAlbumId;
    }

    public void setSpecialAlbumId(String specialAlbumId) {
        this.specialAlbumId = specialAlbumId;
    }

    public String getSpecialSingleId() {
        return specialSingleId;
    }

    public void setSpecialSingleId(String specialSingleId) {
        this.specialSingleId = specialSingleId;
    }

    public String getSpecialSingleTitle() {
        return specialSingleTitle;
    }

    public void setSpecialSingleTitle(String specialSingleTitle) {
        this.specialSingleTitle = specialSingleTitle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIsLove() {
        return isLove;
    }

    public void setIsLove(String isLove) {
        this.isLove = isLove;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.account);
        dest.writeString(this.userImg);
        dest.writeString(this.comment);
        dest.writeInt(this.commentLove);
        dest.writeInt(this.commentReply);
        dest.writeString(this.specialAlbumId);
        dest.writeString(this.specialSingleId);
        dest.writeString(this.specialSingleTitle);
        dest.writeString(this.createdAt);
        dest.writeString(this.isLove);
        dest.writeString(this.userImage);
        dest.writeParcelable(this.single, flags);
    }

    public UserComments() {
    }

    protected UserComments(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.account = in.readString();
        this.userImg = in.readString();
        this.comment = in.readString();
        this.commentLove = in.readInt();
        this.commentReply = in.readInt();
        this.specialAlbumId = in.readString();
        this.specialSingleId = in.readString();
        this.specialSingleTitle = in.readString();
        this.createdAt = in.readString();
        this.isLove = in.readString();
        this.userImage = in.readString();
        this.single = in.readParcelable(Single.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserComments> CREATOR = new Parcelable.Creator<UserComments>() {
        @Override
        public UserComments createFromParcel(Parcel source) {
            return new UserComments(source);
        }

        @Override
        public UserComments[] newArray(int size) {
            return new UserComments[size];
        }
    };
}
