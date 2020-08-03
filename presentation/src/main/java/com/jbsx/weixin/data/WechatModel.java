package com.jbsx.weixin.data;

import android.graphics.Bitmap;

/**
 * 封装微信分享的数据类
 * 3.4增加
 *
 * Created by lijian15 on 2016/9/6.
 */

public class WechatModel {
    private String mTitle;
    private String mDescription;
    private String mShareUrl;
    private Bitmap mIcon;
    private String mIconUrl;
    private String mPath;

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String url) {
        this.mShareUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public void setIcon(Bitmap icon) {
        this.mIcon = icon;
    }
}
