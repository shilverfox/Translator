package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Created by lijian15 on 2018/1/18.
 */

public class CommentsUserCase extends UserCase {
    public CommentsUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    /**
     * 回复评论
     *
     * @param token
     * @param userId
     * @param albumId
     * @param singleId
     * @param content
     * @param commentId
     * @param callback
     */
    public void requestPostComment(String token, String userId, String albumId, String singleId,
                                   String content, String commentId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.postCommentEntity(token, albumId, singleId,
                userId, content, commentId);
        handleSendRequest(entity, callback);
    }

    /**
     * 点赞
     *
     * @param token
     * @param userId
     * @param albumId
     * @param singleId
     * @param commentId
     * @param callback
     */
    public void requestGiveThumb(String token, String userId, String albumId, String singleId,
                                   String commentId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.giveThumbEntity(token, albumId, singleId,
                userId, commentId);
        handleSendRequest(entity, callback);
    }

    /**
     * 举报
     *
     * @param token
     * @param userId
     * @param albumId
     * @param singleId
     * @param commentId
     * @param callback
     */
    public void requestBadComment(String token, String userId, String albumId, String singleId,
                                 String commentId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.isBadComment(token, albumId, singleId,
                userId, commentId);
        handleSendRequest(entity, callback);
    }
}
