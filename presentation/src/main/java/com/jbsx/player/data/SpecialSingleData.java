package com.jbsx.player.data;

import com.app.domain.net.model.BaseDomainData;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.SpecialSingles;

import java.util.List;

/**
 * 片库详情
 */
public class SpecialSingleData {
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

        private SpecialSingles specialSingle;
        private List<Param> param;

        public SpecialSingles getSpecialSingle() {
            return specialSingle;
        }

        public void setSpecialSingle(SpecialSingles specialSingle) {
            this.specialSingle = specialSingle;
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
