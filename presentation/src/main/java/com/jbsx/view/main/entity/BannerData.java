package com.jbsx.view.main.entity;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

public class BannerData {
    private boolean status;
    private int code;
    private String msg;
    private Payload payload;

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

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public class Payload {
        private List<Param> param;
        private List<Banner> bananers;

        public List<Banner> getBananers() {
            return bananers;
        }

        public void setBananers(List<Banner> bananers) {
            this.bananers = bananers;
        }

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public class Banner {
        private String id;
        private String name;
        private String image;
        private String url;
        private String specialAlbumId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSpecialAlbumId() {
            return specialAlbumId;
        }

        public void setSpecialAlbumId(String specialAlbumId) {
            this.specialAlbumId = specialAlbumId;
        }
    }

    public class Param {
        private String specialCode;
        private BaseDomainData.Parameter parameter;

        public String getSpecialCode() {
            return specialCode;
        }

        public void setSpecialCode(String specialCode) {
            this.specialCode = specialCode;
        }

        public BaseDomainData.Parameter getParameter() {
            return parameter;
        }

        public void setParameter(BaseDomainData.Parameter parameter) {
            this.parameter = parameter;
        }
    }
}
