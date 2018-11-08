package com.translatmaster.view.login.data;

import com.app.domain.net.model.BaseDomainData;

import java.util.List;

public class LoginData {
    private boolean status;
    private Result payload;
    private String code;
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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


    public class Result {
        private String id;
        private String account;
        private int accountType;
        private String cellPhone;
        private String email;
        private String organizationId;
        private String organization;
        private int concurrency;
        private int downNum;
        private String token;
        private int state;
        private UserInfo userInfo;
        private BaseDomainData.ResultStatus resultStatus;
        private List<Param> param;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public int getConcurrency() {
            return concurrency;
        }

        public void setConcurrency(int concurrency) {
            this.concurrency = concurrency;
        }

        public int getDownNum() {
            return downNum;
        }

        public void setDownNum(int downNum) {
            this.downNum = downNum;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
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

    public class UserInfo {
        private String birthday;
        private String department;
        private String image;
        private String imageUrl;
        private int isDown;
        private String job;
        private String landingIp;
        private String landingTime;
        private int member;
        private String memberEnd;
        private String memberStart;
        private String nickname;
        private String organization;
        private String realName;
        private String registerTime;
        private int sex;
        private String tel;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getIsDown() {
            return isDown;
        }

        public void setIsDown(int isDown) {
            this.isDown = isDown;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getLandingIp() {
            return landingIp;
        }

        public void setLandingIp(String landingIp) {
            this.landingIp = landingIp;
        }

        public String getLandingTime() {
            return landingTime;
        }

        public void setLandingTime(String landingTime) {
            this.landingTime = landingTime;
        }

        public int getMember() {
            return member;
        }

        public void setMember(int member) {
            this.member = member;
        }

        public String getMemberEnd() {
            return memberEnd;
        }

        public void setMemberEnd(String memberEnd) {
            this.memberEnd = memberEnd;
        }

        public String getMemberStart() {
            return memberStart;
        }

        public void setMemberStart(String memberStart) {
            this.memberStart = memberStart;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }

    public class Param {

        private String account;
        private String pwd;
        private String innerIp;
        private String outIp;
        private String installationId;
        private String system;
        private BaseDomainData.Parameter parameter;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getInnerIp() {
            return innerIp;
        }

        public void setInnerIp(String innerIp) {
            this.innerIp = innerIp;
        }

        public String getOutIp() {
            return outIp;
        }

        public void setOutIp(String outIp) {
            this.outIp = outIp;
        }

        public String getInstallationId() {
            return installationId;
        }

        public void setInstallationId(String installationId) {
            this.installationId = installationId;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public BaseDomainData.Parameter getParameter() {
            return parameter;
        }

        public void setParameter(BaseDomainData.Parameter parameter) {
            this.parameter = parameter;
        }
    }
}
