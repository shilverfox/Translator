package com.jbsx.player.contact;

import com.jbsx.player.data.AlbumData;
import com.jbsx.player.data.ConcernData;
import com.jbsx.player.data.SingleVideoData;
import com.jbsx.player.data.SpecialSingleData;

public class PlayerContact {
    public interface View {
        void createPresenter();

        /**
         * 获得专辑视频列表成功
         * @param albumData
         */
        void drawVideoOfAlbum(AlbumData albumData);

        /**
         * 单一视频信息
         *
         * @param videoData
         */
        void drawSingleVideo(SingleVideoData videoData);

        /**
         * 片库详情
         * @param specialSingleData
         */
        void drawAlbumDetail(SpecialSingleData specialSingleData);

        /**
         * 评论发表成功
         */
        void drawPostSuccess();

        /**
         * 关注结果
         */
        void drawConcernResult(ConcernData data);
    }

    public interface Presenter {
        void start();

        /**
         * 获得专辑中的视频列表
         */
        void loadVideoOfAlbum(String albumId);

        /**
         * 获得某个视频的播放信息
         *
         * @param albumId
         * @param singleId
         */
        void loadSingleVideo(String albumId, String singleId);

        /**
         * 查看片库详情
         *
         * @param albumId
         * @param singleId
         */
        void loadAlbumDetail(String albumId, String singleId);

        /**
         * 发表评论
         *
         * @param albumId
         * @param singleId
         * @param content
         */
        void postComment(String albumId, String singleId, String content);

        /**
         * 收藏片库
         *
         * @param albumId
         * @param singleId
         */
        void handleConcernVideo(String albumId, String singleId);

        /**
         * 记录播放时间
         *
         * @param albumId
         * @param singleId
         * @param second
         */
        void recordWatchTime(String albumId, String singleId, String second);
    }
}
