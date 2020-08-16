package com.jbsx.view.main;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jbsx.R;

import java.io.IOException;

public class AudioPlayer {
    private static AudioPlayer mInstance;

    private MediaPlayer mediaPlayer;

    /** 当前播放的url */
    private String mCurrentSrc;

    /** 上一个播放的view */
    private ImageView mPrePlayerIcon;

    private AudioPlayer() {
        initPlayer();
    }

    public static AudioPlayer getInstance() {
        if (mInstance == null) {
            mInstance = new AudioPlayer();
        }
        return mInstance;
    }

    private void initPlayer() {
        mediaPlayer = new MediaPlayer();
    }

    /**
     * 是否为当前正在播放的资源
     *
     * @param url
     * @return
     */
    private boolean isTheSameUrl(String url) {
        return !TextUtils.isEmpty(url) && !TextUtils.isEmpty(mCurrentSrc) && mCurrentSrc.equals(url);
    }

    public void playMusic(String url, final View loadingView, final ImageView playerIcon) {
        if (isTheSameUrl(url)) {
            // 相同的url，内容一致，直接播放
            startPlayer(playerIcon);
        } else {
            try {
                loadingView.setVisibility(View.VISIBLE);
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        startPlayer(playerIcon);
                        loadingView.setVisibility(View.GONE);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mCurrentSrc = url;
    }

    public void pauseMusic(ImageView playerIcon) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            handlePlayerIcon(playerIcon, false);
        }
    }

    public void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            handlePlayerIcon(mPrePlayerIcon, false);
        }
    }

    private void startPlayer(ImageView playerIcon) {
        if (playerIcon != null && mediaPlayer != null) {
            mediaPlayer.start();

            // 停止上一个，播放当前的
            handlePlayerIcon(mPrePlayerIcon, false);
            handlePlayerIcon(playerIcon, true);
            mPrePlayerIcon = playerIcon;
        }
    }

    private void handlePlayerIcon(ImageView playerIcon, boolean isPlaying) {
        if (playerIcon != null) {
            playerIcon.setImageResource(isPlaying ? R.drawable.album_pause_icon : R.drawable.album_play_icon);
        }
    }
}
