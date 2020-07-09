package com.jbsx.view.myinfo.data;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

public class MyMessageData {
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
        private List<MessageList> messageList;
        private int totalPages;
        private int totalElements;
        private List<Param> param;

        public List<MessageList> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageList> messageList) {
            this.messageList = messageList;
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

    public class MessageList {
        private String id;
        private String reUserId;
        private String reUserName;
        private String reUserImageUrl;
        private String reMessage;
        private String reDate;
        private String userId;
        private String userName;
        private String message;
        private int commentLove;
        private int commentReply;
        private String specialAlbumId;
        private String specialSingleId;
        private String specialSingleTitle;
        private String messageType;

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReUserId() {
            return reUserId;
        }

        public void setReUserId(String reUserId) {
            this.reUserId = reUserId;
        }

        public String getReUserName() {
            return reUserName;
        }

        public void setReUserName(String reUserName) {
            this.reUserName = reUserName;
        }

        public String getReUserImageUrl() {
            return reUserImageUrl;
        }

        public void setReUserImageUrl(String reUserImageUrl) {
            this.reUserImageUrl = reUserImageUrl;
        }

        public String getReMessage() {
            return reMessage;
        }

        public void setReMessage(String reMessage) {
            this.reMessage = reMessage;
        }

        public String getReDate() {
            return reDate;
        }

        public void setReDate(String reDate) {
            this.reDate = reDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
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
