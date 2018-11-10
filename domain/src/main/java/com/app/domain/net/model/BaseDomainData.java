package com.app.domain.net.model;

/**
 * Created by lijian15 on 2018/1/19.
 */

public class BaseDomainData {
    private String code;
    private String msg;
    private boolean status;
    private Result payload;

    public Result getPayload() {
        return payload;
    }

    public void setPayload(Result payload) {
        this.payload = payload;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return status;
    }

    public void setSuccess(boolean success) {
        this.status = success;
    }

    public class Result {
        private ResultStatus resultStatus;

        public ResultStatus getResultStatus() {
            return resultStatus;
        }

        public void setResultStatus(ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
        }
    }

    public class ResultStatus {
        private String status;
        private String message;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class BasePage {

        private int size;
        private int no;
        public void setSize(int size) {
            this.size = size;
        }
        public int getSize() {
            return size;
        }

        public void setNo(int no) {
            this.no = no;
        }
        public int getNo() {
            return no;
        }

    }

    public class Parameter {
        private String token;
        private String remoteAddress;
        private String deviceToken;
        private int deviceType;
        private String installationId;
        private String version;
        private String signature;
        private long timestamp;
        private String domainName;
        private int channel;
        private int platform;
        private String platformCode;
        private String clientIp;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRemoteAddress() {
            return remoteAddress;
        }

        public void setRemoteAddress(String remoteAddress) {
            this.remoteAddress = remoteAddress;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public String getInstallationId() {
            return installationId;
        }

        public void setInstallationId(String installationId) {
            this.installationId = installationId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public String getPlatformCode() {
            return platformCode;
        }

        public void setPlatformCode(String platformCode) {
            this.platformCode = platformCode;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }
    }
}
