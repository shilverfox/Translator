package com.translatmaster.view.login.data;

/**
 * Html模型类，封装登录sdk返回的一些数据
 * 调用H5页面时使用
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 模型类
 */
public class HtmlModal implements Parcelable {
    private String openUrl;
    private String returnUrl;
    private String cookieName;
    private int mRequestSource;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mRequestSource);
        out.writeString(openUrl);
        out.writeString(returnUrl);
        out.writeString(cookieName);
    }

    public static final Parcelable.Creator<HtmlModal> CREATOR
            = new Parcelable.Creator<HtmlModal>() {

        public HtmlModal createFromParcel(Parcel in) {
            return new HtmlModal(in);
        }

        public HtmlModal[] newArray(int size) {
            return new HtmlModal[size];
        }
    };

    private HtmlModal(Parcel in) {
        mRequestSource = in.readInt();
        openUrl = in.readString();
        returnUrl = in.readString();
        cookieName = in.readString();
    }

    public HtmlModal() {

    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public int getRequestSource() {
        return mRequestSource;
    }

    public void setRequestSource(int requestSource) {
        mRequestSource = requestSource;
    }
}