package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Created by lijian15 on 2018/1/18.
 */

public class LoginViewUserCase extends UserCase {
    public LoginViewUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    public void modifyPassword(String token, String userId, String oldPass, String newPass,
                               BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getModifyPasswordEntity(token, userId, oldPass,
                newPass);
        handleSendRequest(entity, callback);
    }

    public void updateUserInfo(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getUpdateUserInfoEntity();
        handleSendRequest(entity, callback);
    }

    public void verifySmsCode(String phone, String smsCode, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getVerifySmsCodeEntity(phone, smsCode);
        handleSendRequest(entity, callback);
    }

    public void getUserInfo(String userId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getUserInfoEntity(userId);
        handleSendRequest(entity, callback);
    }

    public void register(String phone, String password, String smsCode, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getRegisterEntity(phone, password, smsCode);
        handleSendRequest(entity, callback);
    }

    public void logOut(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getLogOutEntity();
        handleSendRequest(entity, callback);
    }

    public void loginByUser(String id, String password, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getLoginByUserEntity(id, password);
        handleSendRequest(entity, callback);
    }

    public void loginByWechat(String id, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getLoginByWechatEntity(id);
        handleSendRequest(entity, callback);
    }

    public void bindUser(String userId, String phone, String wxId, int type, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getBindUserEntity(userId, phone, wxId, type);
        handleSendRequest(entity, callback);
    }

    public void unBind(String userId, String phone, String wxId, int type, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getUnBindEntity(userId, phone, wxId, type);
        handleSendRequest(entity, callback);
    }

    public void requestSmsCode(int type, String cellPhone, String t, String s, String r, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getRequestSmsCodeEntity(type, cellPhone, t, s, r);
        handleSendRequest(entity, callback);
    }

    public void resetPassword(String phone, String password, String smsCode, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getResetPasswordEntity(phone, password, smsCode);
        handleSendRequest(entity, callback);
    }
}
