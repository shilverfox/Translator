package com.jbsx.view.myinfo.data;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

public class MyCommentData {
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
        private List<UserComments> userComments;
        private int totalPages;
        private int totalElements;
        private BaseDomainData.ResultStatus resultStatus;
        private List<Param> param;

        public List<UserComments> getUserComments() {
            return userComments;
        }

        public void setUserComments(List<UserComments> userComments) {
            this.userComments = userComments;
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

    public class UserComments {
        private String id;
        private String userId;
        private String account;
        private String userImg;
        private String comment;
        private int commentLove;
        private int commentReply;
        private String specialAlbumId;
        private String specialSingleId;
        private String specialSingleTitle;
        private String createdAt;
        private String isLove;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getCommentLove() {
            return commentLove;
        }

        public void setCommentLove(int commentLove) {
            this.commentLove = commentLove;
        }

        public int getCommentReply() {
            return commentReply;
        }

        public void setCommentReply(int commentReply) {
            this.commentReply = commentReply;
        }

        public String getSpecialAlbumId() {
            return specialAlbumId;
        }

        public void setSpecialAlbumId(String specialAlbumId) {
            this.specialAlbumId = specialAlbumId;
        }

        public String getSpecialSingleId() {
            return specialSingleId;
        }

        public void setSpecialSingleId(String specialSingleId) {
            this.specialSingleId = specialSingleId;
        }

        public String getSpecialSingleTitle() {
            return specialSingleTitle;
        }

        public void setSpecialSingleTitle(String specialSingleTitle) {
            this.specialSingleTitle = specialSingleTitle;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getIsLove() {
            return isLove;
        }

        public void setIsLove(String isLove) {
            this.isLove = isLove;
        }
    }

    public class Param {
        private String specialCode;
        private int type;
        private BaseDomainData.BasePage basePage;
        private BaseDomainData.Parameter parameter;

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
