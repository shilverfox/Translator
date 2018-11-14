package com.app.domain.net.data;

import android.text.TextUtils;

import com.app.domain.net.model.BaseBody;
import com.app.domain.net.model.BaseHeader;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.RequestConst;
import com.google.gson.Gson;

/**
 * List all the http request.
 *
 * Created by lijian15 on 2016/12/14.
 */

public class HttpRequestPool {

    /**
     * Generate url that can be handled by Google API.
     * formate to: q="This is a beautiful day!&target=es&format=text&source=en&key=" + APP_KEY;
     *
     * @param content string that need to be translated
     * @return
     */
    public static BaseRequestEntity getTranslateResult(String content, String src, String dest) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.GOOGLE_TRANS_URL);

        BaseBody body = new BaseBody();
        body.add("q", content);
        body.add("target", dest);
        body.add("format", "text");
        body.add("source", "en");
        body.add("key", ConstData.GOOGLE_APP_KEY);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getTestEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
//        body.add("deviceId", "5d56134c146fc3b6923c2e66dd774175");
//        body.add("networkType", "WIFI");
//        body.add("screen", "2392*1440");
//        body.add("appName", "Paidaojia");
//        body.add("signKey", "e30d48a05849463873b87f709a534134");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * Download the patch
     *
     * @return
     */
    public static BaseRequestEntity getHotFixPatch() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOT_PATCH_URL);
        baseRequest.setmNeedByteData(true);

        return baseRequest;
    }

    /**
     * Just for testing
     *
     * @param url
     * @return
     */
    public static BaseRequestEntity mockLocation(String url) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(url);
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("ak", "21811227");
        body.add("av", "3.0.0");
        body.add("c", "10004852");
        body.add("s", "e339dd7bf87a6cba609e56e9506108e3d02ae33f");
        body.add("d", "V9%2B0VkEIMsUDAFPyuDC6WP7R");
        body.add("sv", "4.4.1");
        body.add("p", "a");
        body.add("t", "1486435258768");
        body.add("is", "1");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }


    public static BaseRequestEntity getModifyPasswordEntity(String userId, String oldPass, String newPass) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getUpdateUserInfoEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getVerifySmsCodeEntity(String phone, String smsCode) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getUserInfoEntity(String userId) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getRegisterEntity(String phone, String password, String smsCode) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/registerAccount");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("cellPhone", phone);
        body.add("pwd", password);
        body.add("verifyCode", smsCode);
        body.add("type", ConstData.REGISTER_USER_TYPE);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getLogOutEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getLoginByUserEntity(String id, String password) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/login");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("account", id);
        body.add("pwd", password);
        body.add("innerIp", "127.0.0.1");
        body.add("outIp", "127.0.0.1");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getBindUserEntity(String userId, String phone, String wxId, int type) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getUnBindEntity(String userId, String phone, String wxId, int type) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getHotSingleList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getRequestSmsCodeEntity(int type, String cellPhone) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/requestSmsCodeByApp");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("type", type + "");
        body.add("cellphone", cellPhone);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getResetPasswordEntity(String phone, String password, String smsCode) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/resetPassword");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        // TODO: 2018/11/8   cellphone 字段名字和注册不一样，大P和小p
        BaseBody body = new BaseBody();
        body.add("cellphone", phone);
        body.add("pwd", password);
        body.add("verifyCode", smsCode);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }


    public static BaseRequestEntity getMainPageBannerInfoEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/bananer");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 首页主讲嘉宾
     * @return
     */
    public static BaseRequestEntity getMainPageHostListEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getIndexCelebrity");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 专题列表
     */
    public static BaseRequestEntity getMainPageSpecialAlbumEntity(int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        // TODO: 2018/11/12 /Special/Special/getIndexAlbumList 
        baseRequest.setFunctionId("/Special/Special/getAllAlbumList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("no", page + "");
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");


        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 获取首页的名家列表
     *
     * @return
     */
    public static BaseRequestEntity getMainPageCelebritiesEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getIndexCelebrity");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 片库tab，获取名家列表
     * @return
     */
    public static BaseRequestEntity getRepertoryCelebrtiesEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getCelebrityByFirstLetter");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 片库tab，获取名家对应的视频列表
     *
     * @param celebrityId 名家id
     * @return
     */
    public static BaseRequestEntity getRepertoryListEntity(int celebrityId, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/search");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("no", page + "");
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");

        if (celebrityId > 0) {
            body.add("celebrityIds[]", celebrityId + "");
        }


//        String test = "[19, 20]";
//        Gson testGson = new Gson();
//        int result[] = testGson.fromJson(test, int[].class);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 查看观看历史
     *
     * @param token
     * @param page
     * @return
     */
    public static BaseRequestEntity getMyVideoHistoryEntity(String token, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getUserHistory");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("no", page + "");
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    public static BaseRequestEntity getDeleteHistoryEntity(String token) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getUserHistory");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }
}
