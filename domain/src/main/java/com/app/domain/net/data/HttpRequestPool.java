package com.app.domain.net.data;

import android.text.TextUtils;

import com.app.domain.net.model.BaseBody;
import com.app.domain.net.model.BaseHeader;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.RequestConst;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

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
     * 心跳数据
     *
     * @return
     */
    public static BaseRequestEntity getHeartBeatEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/app/deviceHeartbeat");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

        return baseRequest;
    }

    /**
     * 获取首页导航数据
     *
     * @return
     */
    public static BaseRequestEntity getNavigationEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/app/getNav");
        baseRequest.setMethod(RequestConst.REQUEST_GET);

        BaseBody body = new BaseBody();
        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

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


    public static BaseRequestEntity getModifyPasswordEntity(String token, String userId,
                                                            String oldPass, String newPass) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/modifyPassword");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("oldPwd", oldPass);
        body.add("newPwd", newPass);
        body.add("userId", userId);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

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

    public static BaseRequestEntity getLoginByWechatEntity(String id) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/wechatLogin");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("code", id);
        body.add("innerIp", "127.0.0.1");
        body.add("outIp", "127.0.0.1");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    public static BaseRequestEntity getBindUserEntity(String userId, String phone, String wxId, int type) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/bindAccount");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        if (!TextUtils.isEmpty(phone)) {
            body.add("cellphone", phone);
        }

        if (!TextUtils.isEmpty(phone)) {
            body.add("openId", wxId);
        }

        if (!TextUtils.isEmpty(phone)) {
            body.add("userId", userId);
        }

        body.add("type", type);

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

    /**
     * 获取短信验证码
     *
     * @param type
     * @param cellPhone
     * @param t 时间戳
     * @param s key
     * @param r 随机数
     * @return
     */
    public static BaseRequestEntity getRequestSmsCodeEntity(int type, String cellPhone, String t,
                                                            String s, String r) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/requestSmsCodeByApp");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("type", type + "");
        body.add("cellphone", cellPhone);

        if (!TextUtils.isEmpty(t)) {
            body.add("t", t);
        }

        if (!TextUtils.isEmpty(s)) {
            body.add("s", s);
        }

        if (!TextUtils.isEmpty(r)) {
            body.add("r", r);
        }

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
        baseRequest.setFunctionId("/app/index");
        baseRequest.setMethod(RequestConst.REQUEST_GET);

        BaseBody body = new BaseBody();
        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

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
     *
     * @param showAll true:显示全部，用于首页的专辑tab，false第一个tab中的专题
     */
    public static BaseRequestEntity getMainPageSpecialAlbumEntity(int page, boolean showAll) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);

        String function = showAll ? "getAllAlbumList" : "getIndexAlbumList";
        baseRequest.setFunctionId("/Special/Special/" + function);
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("no", page + "");
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");


        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 一级分类数据
     */
    public static BaseRequestEntity getMainPageGalleryEntity(String classifyCode) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);

        baseRequest.setFunctionId("/app/classify");
        baseRequest.setMethod(RequestConst.REQUEST_GET);
        BaseBody body = new BaseBody();
        body.add("classifyCode", classifyCode);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

        return baseRequest;
    }

    /**
     * 获取专辑详情
     */
    public static BaseRequestEntity getAlbumDetailEntity(String classifyCode) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);

        baseRequest.setFunctionId("/app/albumDetail");
        BaseBody body = new BaseBody();
        body.add("albumCode", classifyCode);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

        return baseRequest;
    }

    /**
     * 获取视频详情
     */
    public static BaseRequestEntity getVideoDetailEntity(String classifyCode) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);

        baseRequest.setFunctionId("/app/videoDetail");
        baseRequest.setMethod(RequestConst.REQUEST_GET);
        BaseBody body = new BaseBody();
        body.add("videoCode", classifyCode);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

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
     * 搜索
     *
     * @param celebrityId
     * @param keyWord 搜索关键字
     * @param type 0 标题+主讲 2主讲 1标题 (默认值：0)
     * @param sort 排序是否热门1热门2非热门3集数正序4集数倒叙5名称正序6名称倒叙
     * @param page
     * @return
     */
    public static BaseRequestEntity getSearchEntity(int celebrityId, String keyWord, int type,
                                                    int sort, int page) {
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

        if (!TextUtils.isEmpty(keyWord)) {
            body.add("where", keyWord);
        }

        if (type >= 0 && type <= 2) {
            body.add("type", type + "");
        }

        body.add("sort", sort + "");


//        String test = "[19, 20]";
//        Gson testGson = new Gson();
//        int result[] = testGson.fromJson(test, int[].class);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 获得专辑下的视频列表
     *
     * @param classifyCode
     * @param page
     * @return
     */
    public static BaseRequestEntity getVideoFeedEntity(String classifyCode, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/app/video");

        BaseBody body = new BaseBody();
        body.add("classifyCode", classifyCode);
        body.add("pageNum", page + "");
        body.add("pageSize", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

        return baseRequest;
    }

    /**
     * 获得本地视频列表
     *
     * @param typeId
     * @param page
     * @return
     */
    public static BaseRequestEntity getLocalVideoFeedEntity(String typeId, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/app/localVideoList");

        BaseBody body = new BaseBody();
        body.add("key", typeId);
//        body.add("pageNum", page + "");
//        body.add("pageSize", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

        return baseRequest;
    }

    /**
     * 获得专辑列表
     *
     * @param classifyCode
     * @param page
     * @return
     */
    public static BaseRequestEntity getAlbumFeedEntity(String classifyCode, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/app/album");

        BaseBody body = new BaseBody();
        body.add("classifyCode", classifyCode);
        body.add("pageNum", page + "");
        body.add("pageSize", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

        return baseRequest;
    }

    /**
     * 检索
     *
     * @param keyWord
     * @param searchType searchType
     * @param page
     * @return
     */
    public static BaseRequestEntity getSearchResultEntity(String keyWord, int searchType, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/app/search");

        BaseBody body = new BaseBody();
        body.add("key", keyWord);
        body.add("type", searchType + "");
        body.add("pageNum", page + "");
        body.add("pageSize", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, "");

        return baseRequest;
    }

    /**
     * 删除我的收藏、观看历史数据
     *
     * @param token
     * @param isHistory true,删除历史， false:删除收藏
     * @return
     */
    public static BaseRequestEntity getDeleteVideoEntity(String token, String[] ids, boolean isHistory) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId(isHistory ? "/Special/Special/delAppSingleHistory" : "/Special/Special/deleteCollect");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        if (ids != null && ids.length > 0) {
            body.add("idList", ids);
        }

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 获取我的评论列表
     *
     * @param token
     * @param type 2评论消息3点赞消息
     * @param page
     * @return
     */
    public static BaseRequestEntity getMyMessageEntity(String token, String type, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getMessageList");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("type", type);
        body.add("no", page + "");
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 根据id获取用户信息
     *
     * @param token
     * @param id
     * @return
     */
    public static BaseRequestEntity getUserInfoEntity(String token, String id) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/getUserByUserId");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("userId", id);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 关于页面的list
     *
     * @return
     */
    public static BaseRequestEntity getAboutEntity() {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/about");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 更改用户信息
     *
     * @return
     */
    public static BaseRequestEntity getModifyUserInfoEntity(String token, String userId,
                                                            String nickName, String imgId) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/User/User/updateUser");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("userId", userId);

        if (!TextUtils.isEmpty(nickName)) {
            body.add("nickname", nickName);
        }

        if (!TextUtils.isEmpty(imgId)) {
            body.add("image", imgId);
        }

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 上传用户头像
     *
     * @param token
     * @param userId
     * @return
     */
    public static BaseRequestEntity getUploadUserHeadEntity(String token, String userId,
                                                            String fileName, File file) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Material/Material/getFileToken");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("userId", userId);
        body.add("fileType", 1);

//        body.setFile(file);
//        body.setFileName(fileName);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }


    /**
     * 把上传7牛的结果信息告诉jb服务器
     *
     * @param token
     * @param id
     * @param name
     * @param size
     * @param format
     * @param fileObject
     * @param bucket
     * @return
     */
    public static BaseRequestEntity getUpload7NiuResultEntity(String token, String id, String name,
                                                              String size, String format,
                                                              JSONObject fileObject, String bucket) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Material/Material/updateFile");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("id", id);
        body.add("name", name);
        body.add("size", size);
        body.add("format", format);
        body.add("fileObject", fileObject);
        body.add("bucket", bucket);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 获取当前用户所有评论
     *
     * @param token
     * @param type 1:我对视频的所有评论, 2:我的所有赞,3:我对用户的所有评论
     * @param page
     * @return
     */
    public static BaseRequestEntity getMyCommentEntity(String token, String type, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getUserAllComment");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("type", type);
        body.add("no", page + "");
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 删除一条评论
     *
     * @param token
     * @param commentId
     * @return
     */
    public static BaseRequestEntity getDeleteCommentEntity(String token, String commentId, String userId) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/delComment");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("commentId", commentId);
        body.add("userId", userId);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 获得当前评论对应的评论列表
     *
     * @param token
     * @param commentId
     * @param albumId
     * @param singleId
     * @param userId
     * @param page
     * @return
     */
    public static BaseRequestEntity getCommentDetailListEntity(String token, String commentId,
                                                               String albumId, String singleId,
                                                               String userId, int page) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getReplyInComment");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("commentId", commentId);
        body.add("albumId", albumId);
        body.add("singleId", singleId);
        body.add("userId", userId);
        body.add("mode", ConstData.COMMENT_ORDER_MODE_TIME); // 1最新评论，0：最热
        body.add("no", page + "");
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 查看专辑的视频列表
     *
     * @param albumId
     * @param userId
     * @return
     */
    public static BaseRequestEntity getVideoListOfAlbumEntity(String albumId, String userId) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getAlbumTheSingles");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("albumId", albumId);
        body.add("userId", userId);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 获得某一个视频的播放信息
     *
     * @param token
     * @param singleId
     * @return
     */
    public static BaseRequestEntity getVideoPlayInfoEntity(String token, String singleId, String type) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getSinglePlayUrl");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("singleId", singleId);
        body.add("type", type);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 查看片库详情
     *
     * @param albumId
     * @param singleId
     * @return
     */
    public static BaseRequestEntity getAlbumDetailEntity(String albumId, String singleId) {
        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/toSingleView");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("albumId", albumId);
        body.add("singleId", singleId);

        baseRequest.setBaseBody(body);

        return baseRequest;
    }

    /**
     * 获得某个视频的评论列表
     *
     * @param token
     * @param albumId
     * @param singleId
     * @param page
     * @return
     */
    public static BaseRequestEntity getVideoCommentsEntity(String token, String albumId,
                                                           String singleId, int page) {

        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/getComment");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("albumId", albumId);
        body.add("singleId", singleId);
        body.add("no", page + "");
        body.add("mode", ConstData.COMMENT_ORDER_MODE_TIME);
        body.add("size", ConstData.DEFAULT_PAGE_SIZE + "");

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 发表评论（视频评论，评论的评论）
     *
     * @param token
     * @param albumId
     * @param singleId
     * @param userId
     * @param content
     * @param commentId (评论ID，若属发表的评论，此参数可忽略
     * @return
     */
    public static BaseRequestEntity postCommentEntity(String token, String albumId, String singleId,
                                                      String userId, String content, String commentId) {

        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/tooComment");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        if (!TextUtils.isEmpty(albumId)) {
            body.add("albumId", albumId);
        }


        if (!TextUtils.isEmpty(singleId)) {
            body.add("singleId", singleId);
        }

        if (!TextUtils.isEmpty(userId)) {
            body.add("userId", userId);
        }

        if (!TextUtils.isEmpty(content)) {
            body.add("content", content);
        }

        if (!TextUtils.isEmpty(commentId)) {
            body.add("commentId", commentId);
        }

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 点赞
     *
     * @param token
     * @param albumId
     * @param singleId
     * @param userId
     * @param commentId
     * @return
     */
    public static BaseRequestEntity giveThumbEntity(String token, String albumId, String singleId,
                                                      String userId, String commentId) {

        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/tooLove");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");

        if (!TextUtils.isEmpty(albumId)) {
            body.add("albumId", albumId);
        }


        if (!TextUtils.isEmpty(singleId)) {
            body.add("singleId", singleId);
        }

        if (!TextUtils.isEmpty(userId)) {
            body.add("userId", userId);
        }

        if (!TextUtils.isEmpty(commentId)) {
            body.add("commentId", commentId);
        }

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 评论举报
     *
     * @param token
     * @param albumId
     * @param singleId
     * @param userId
     * @param commentId
     * @return
     */
    public static BaseRequestEntity isBadComment(String token, String albumId, String singleId,
                                                    String userId, String commentId) {

        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/tooReport");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("source", "1");

        if (!TextUtils.isEmpty(albumId)) {
            body.add("albumId", albumId);
        }


        if (!TextUtils.isEmpty(singleId)) {
            body.add("singleId", singleId);
        }

        if (!TextUtils.isEmpty(userId)) {
            body.add("userId", userId);
        }

        if (!TextUtils.isEmpty(commentId)) {
            body.add("commentId", commentId);
        }

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 关注、取关视频
     *
     * @param token
     * @param albumId
     * @param singleId
     * @param userId
     * @return
     */
    public static BaseRequestEntity concernVideoEntity(String token, String albumId, String singleId,
                                                      String userId) {

        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/collectSingle");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("albumId", albumId);
        body.add("singleId", singleId);
        body.add("userId", userId);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }

    /**
     * 记录播放时长
     *
     * @param token
     * @param albumId
     * @param singleId
     * @param userId
     * @param second
     * @return
     */
    public static BaseRequestEntity recordWatchTimeEntity(String token, String albumId, String singleId,
                                                       String userId, String second) {

        BaseRequestEntity baseRequest = new BaseRequestEntity();
        baseRequest.setUrl(ConstData.HOST);
        baseRequest.setFunctionId("/Special/Special/writeSinglePlayTime");
        baseRequest.setMethod(RequestConst.REQUEST_POST);

        BaseBody body = new BaseBody();
        body.add("specialCode", "JBSX");
        body.add("albumId", albumId);
        body.add("singleId", singleId);
        body.add("userId", userId);
        body.add("second", second);

        baseRequest.setBaseBody(body);
        HttpRequestUtil.getHeader(baseRequest, token);

        return baseRequest;
    }
}
