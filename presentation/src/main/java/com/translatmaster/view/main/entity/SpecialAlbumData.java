package com.translatmaster.view.main.entity;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

/**
 * 专题系列
 *
 * Created by lijian on 2018/11/10.
 */

public class SpecialAlbumData {
    private boolean status;
    private int code;
    private String msg;
    private Payload payload;

    public class Payload {

        private List<String> singles;
        private BaseDomainData.ResultStatus resultStatus;
        private List<Param> param;

        public void setSingles(List<String> singles) {
            this.singles = singles;
        }
        public List<String> getSingles() {
            return singles;
        }

        public void setResultStatus(BaseDomainData.ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
        }
        public BaseDomainData.ResultStatus getResultStatus() {
            return resultStatus;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
        public List<Param> getParam() {
            return param;
        }

    }

    public class Param {

        private String specialCode;
        private String albumId;
        private String singleId;
        private String userId;
        private int type;
        private BaseDomainData.Parameter parameter;

        public String getSpecialCode() {
            return specialCode;
        }

        public void setSpecialCode(String specialCode) {
            this.specialCode = specialCode;
        }

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public BaseDomainData.Parameter getParameter() {
            return parameter;
        }

        public void setParameter(BaseDomainData.Parameter parameter) {
            this.parameter = parameter;
        }
    }
}
