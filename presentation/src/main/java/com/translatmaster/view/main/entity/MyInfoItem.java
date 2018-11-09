package com.translatmaster.view.main.entity;

public class MyInfoItem {
    private int id;
    private String title;
    private int imgId;
    private boolean notice;
    private boolean topDeliver;
    private boolean bottomDeliver;
    private boolean rightDeliver;
    private CharSequence hint;
    private String hintColor;
    private String openUrl;
    private String pinType;
    private Class to;

    public MyInfoItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Class getTo() {
        return to;
    }

    public void setTo(Class to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public boolean isTopDeliver() {
        return topDeliver;
    }

    public void setTopDeliver(boolean topDeliver) {
        this.topDeliver = topDeliver;
    }

    public boolean isBottomDeliver() {
        return bottomDeliver;
    }

    public void setBottomDeliver(boolean bottomDeliver) {
        this.bottomDeliver = bottomDeliver;
    }

    public boolean isRightDeliver() {
        return rightDeliver;
    }

    public void setRightDeliver(boolean rightDeliver) {
        this.rightDeliver = rightDeliver;
    }

    public CharSequence getHint() {
        return hint;
    }

    public void setHint(CharSequence hint) {
        this.hint = hint;
    }

    public String getHintColor() {
        return hintColor;
    }

    public void setHintColor(String hintColor) {
        this.hintColor = hintColor;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public String getPinType() {
        return pinType;
    }

    public void setPinType(String pinType) {
        this.pinType = pinType;
    }
}
