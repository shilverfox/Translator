package com.jbsx.view.main.entity;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

/**
 * 专题系列
 *
 * Created by lijian on 2018/11/10.
 */

public class SpecialAlbumData {
    private boolean status;

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

    private int code;
    private String msg;
    private Payload payload;



    public class Payload {
        private List<SpecialAlbums> specialAlbums;
        private int totalPages;
        private int totalElements;
        private List<Param> param;

        public List<SpecialAlbums> getSpecialAlbums() {
            return specialAlbums;
        }

        public void setSpecialAlbums(List<SpecialAlbums> specialAlbums) {
            this.specialAlbums = specialAlbums;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public class Param {
        private String specialCode;
        private BaseDomainData.BasePage basePage;
        private BaseDomainData.Parameter parameter;

        public String getSpecialCode() {
            return specialCode;
        }

        public void setSpecialCode(String specialCode) {
            this.specialCode = specialCode;
        }

        public BaseDomainData.BasePage getBasePage() {
            return basePage;
        }

        public void setBasePage(BaseDomainData.BasePage basePage) {
            this.basePage = basePage;
        }

        public BaseDomainData.Parameter getParameter() {
            return parameter;
        }

        public void setParameter(BaseDomainData.Parameter parameter) {
            this.parameter = parameter;
        }
    }
}
