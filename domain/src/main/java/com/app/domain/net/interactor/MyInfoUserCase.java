package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

import org.json.JSONObject;

import java.io.File;

public class MyInfoUserCase extends UserCase {
    public MyInfoUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    /**
     * 删除收藏、观看历史
     *
     * @param token
     * @param isHistory
     * @param callback
     */
    public void requestDeleteVideo(String token, String[] ids, boolean isHistory, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getDeleteVideoEntity(token, ids, isHistory);
        handleSendRequest(entity, callback);
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @param callback
     */
    public void deleteComment(String token, String commentId, String userId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getDeleteCommentEntity(token, commentId, userId);
        handleSendRequest(entity, callback);
    }

    /**
     * 根据id获取用户信息
     *
     * @param token
     * @param ids
     * @param callback
     */
    public void requestUserInfo(String token, String ids, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getUserInfoEntity(token, ids);
        handleSendRequest(entity, callback);
    }

    /**
     * 关于页面中的介绍
     *
     * @param callback
     */
    public void requestAboutInfo(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getAboutEntity();
        handleSendRequest(entity, callback);
    }

    /**
     * 更改用户信息
     *
     * @param callback
     */
    public void requestModifyUserInfo(String token, String userId, String nickName, String imgId,
                                      BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getModifyUserInfoEntity(token, userId, nickName,
                imgId);
        handleSendRequest(entity, callback);
    }

    /**
     * 上传用户头像
     *
     * @param token
     * @param userId
     * @param callback
     */
    public void requestUploadUserHead(String token, String userId, String fileName, File file,
                                      BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getUploadUserHeadEntity(token, userId, fileName, file);
        handleSendRequest(entity, callback);
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
     * @param callback
     */
    public void requestUpload7NiuResult(String token, String id, String name, String size,
                                        String format, JSONObject fileObject, String bucket,
                                        BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getUpload7NiuResultEntity(token, id, name, size,
                format, fileObject, bucket);
        handleSendRequest(entity, callback);
    }
}
