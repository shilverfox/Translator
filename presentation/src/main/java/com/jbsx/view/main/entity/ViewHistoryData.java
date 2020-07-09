package com.jbsx.view.main.entity;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

/**
 * 我的视频历史
 */
public class ViewHistoryData {
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
        private List<UserSingle> userSingles;
        private int totalPages;
        private int totalElements;
        private List<Param> param;

        public List<UserSingle> getUserSingles() {
            return userSingles;
        }

        public void setUserSingles(List<UserSingle> userSingles) {
            this.userSingles = userSingles;
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
        private List<String> celebrityIds;
        private String where;
        private String specialCode;
        private int type;
        private String initial;
        private int sort;
        private BaseDomainData.BasePage basePage;
        private BaseDomainData.Parameter parameter;

        public List<String> getCelebrityIds() {
            return celebrityIds;
        }

        public void setCelebrityIds(List<String> celebrityIds) {
            this.celebrityIds = celebrityIds;
        }

        public String getWhere() {
            return where;
        }

        public void setWhere(String where) {
            this.where = where;
        }

        public String getSpecialCode() {
            return specialCode;
        }

        public void setSpecialCode(String specialCode) {
            this.specialCode = specialCode;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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
