package com.jbsx.player.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.PlayerUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.player.contact.PlayerContact;
import com.jbsx.player.data.AlbumData;
import com.jbsx.player.data.ConcernData;
import com.jbsx.player.data.SingleVideoData;
import com.jbsx.player.data.SpecialSingleData;
import com.jbsx.utils.MessageTools;
import com.jbsx.view.login.util.LoginHelper;

public class PlayerPresenter implements PlayerContact.Presenter {
    private final static String TAG = "PlayerPresenter";

    private PlayerContact.View mView;
    private PlayerUserCase mUserCase;

    public PlayerPresenter(PlayerContact.View view, PlayerUserCase userCase) {
        mView = view;
        mUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadVideoOfAlbum(String albumId) {
        mUserCase.requestVideoListOfAlbum(albumId, LoginHelper.getInstance().getUserId(), new BaseRequestCallback() {
            @Override
            public void onRequestFailed(BaseDomainData data) {
                handleLoadVideoListFailed(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleLoadVideoListSuccessful(data);
            }

            @Override
            public void onNetError() {

            }
        });
    }

    private void handleLoadVideoListSuccessful(String data) {
        AlbumData albumData = ParseUtil.parseData(data, AlbumData.class);
        if (mView != null) {
            mView.drawVideoOfAlbum(albumData);
        }
    }

    private void handleLoadVideoListFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }

    @Override
    public void loadSingleVideo(String singleId, String type) {
        mUserCase.requestSingleVideo(LoginHelper.getInstance().getUserToken(), singleId, type,
                new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        handleLoadSingleVideoFailed(data);
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        handleLoadSingleVideoSuccessful(data);
                    }

                    @Override
                    public void onNetError() {

                    }
                });
    }

    private void handleLoadSingleVideoSuccessful(String data) {
        SingleVideoData videoData = ParseUtil.parseData(data, SingleVideoData.class);
        if (mView != null) {
            mView.drawSingleVideo(videoData);
        }
    }

    private void handleLoadSingleVideoFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }

    @Override
    public void loadAlbumDetail(String albumId, String singleId) {
        mUserCase.requestAlbumDetail(albumId, singleId, new BaseRequestCallback() {
            @Override
            public void onRequestFailed(BaseDomainData data) {
                handleLoadAlbumDetailFailed(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleLoadAlbumDetailSuccessful(data);
            }

            @Override
            public void onNetError() {

            }
        });
    }

    private void handleLoadAlbumDetailSuccessful(String data) {
        SpecialSingleData videoData = ParseUtil.parseData(data, SpecialSingleData.class);
        if (mView != null) {
            mView.drawAlbumDetail(videoData);
        }
    }

    private void handleLoadAlbumDetailFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }

    /**
     * 发表评论
     *
     * @param albumId
     * @param singleId
     * @param content
     */
    @Override
    public void postComment(String albumId, String singleId, String content) {
        mUserCase.requestPostComment(LoginHelper.getInstance().getUserToken(),
                LoginHelper.getInstance().getUserId(), albumId, singleId, content,
                new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        handlePostCommentFailed(data);
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        handlePostCommentSuccessful(data);
                    }

                    @Override
                    public void onNetError() {

                    }
                });
    }

    private void handlePostCommentSuccessful(String data) {
        if (mView != null) {
            mView.drawPostSuccess();
        }
    }

    private void handlePostCommentFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }

    /**
     * 收藏片库
     *
     * @param albumId
     * @param singleId
     */
    @Override
    public void handleConcernVideo(String albumId, String singleId) {
        mUserCase.requestConcernVideo(LoginHelper.getInstance().getUserToken(), albumId, singleId,
                LoginHelper.getInstance().getUserId(), new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        handleConcernVideoFailed(data);
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        handleConcernVideoSuccessful(data);
                    }

                    @Override
                    public void onNetError() {

                    }
                });
    }

    private void handleConcernVideoSuccessful(String data) {
        ConcernData concernData = ParseUtil.parseData(data, ConcernData.class);
        if (mView != null) {
            mView.drawConcernResult(concernData);
        }
    }

    private void handleConcernVideoFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }
}
