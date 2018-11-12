package com.translatmaster.view.main.entity;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

/**
 * 片库列表项
 * 首页片库
 */
public class RepertoryData {
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
        private List<SpecialSingles> specialSingles;
        private int totalPages;
        private int totalElements;
        private BaseDomainData.ResultStatus resultStatus;
        private List<Param> param;

        public List<SpecialSingles> getSpecialSingles() {
            return specialSingles;
        }

        public void setSpecialSingles(List<SpecialSingles> specialSingles) {
            this.specialSingles = specialSingles;
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

        public BaseDomainData.ResultStatus getResultStatus() {
            return resultStatus;
        }

        public void setResultStatus(BaseDomainData.ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
        }

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public class SpecialSingles {
        private Single single;
        private List<Celebrities> celebrities;

        public Single getSingle() {
            return single;
        }

        public void setSingle(Single single) {
            this.single = single;
        }

        public List<Celebrities> getCelebrities() {
            return celebrities;
        }

        public void setCelebrities(List<Celebrities> celebrities) {
            this.celebrities = celebrities;
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
