package com.jbsx.view.main.entity;

import java.util.List;

public class LocalPictureGalleryData {
    private boolean status;
    private int code;
    private String msg;
    private List<PictureItem> body;

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

    public List<PictureItem> getBody() {
        return body;
    }

    public void setBody(List<PictureItem> body) {
        this.body = body;
    }

    public class PictureItem {
        private String atlasCode;
        private String pictureCode;
        private String pictureDesc;
        private String pictureFilePath;
        private String pictureOrder;
        private boolean cove;

        public String getAtlasCode() {
            return atlasCode;
        }

        public void setAtlasCode(String atlasCode) {
            this.atlasCode = atlasCode;
        }

        public String getPictureCode() {
            return pictureCode;
        }

        public void setPictureCode(String pictureCode) {
            this.pictureCode = pictureCode;
        }

        public String getPictureDesc() {
            return pictureDesc;
        }

        public void setPictureDesc(String pictureDesc) {
            this.pictureDesc = pictureDesc;
        }

        public String getPictureFilePath() {
            return pictureFilePath;
        }

        public void setPictureFilePath(String pictureFilePath) {
            this.pictureFilePath = pictureFilePath;
        }

        public String getPictureOrder() {
            return pictureOrder;
        }

        public void setPictureOrder(String pictureOrder) {
            this.pictureOrder = pictureOrder;
        }

        public boolean isCove() {
            return cove;
        }

        public void setCove(boolean cove) {
            this.cove = cove;
        }
    }
}
