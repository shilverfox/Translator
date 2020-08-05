package com.jbsx.view.main;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;

import java.io.IOException;

public class AudioPlayer {
    private static AudioPlayer mInstance;

    private MediaPlayer mediaPlayer;

    /** 当前播放的url */
    private String mCurrentSrc;

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

    public void playMusic(String url, final View loadingView) {
        if (isTheSameUrl(url)) {
            // 相同的url，内容一致，直接播放
            mediaPlayer.start();
        } else {
            try {
                loadingView.setVisibility(View.VISIBLE);
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        loadingView.setVisibility(View.GONE);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mCurrentSrc = url;
    }

    public void pauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mCurrentSrc = null;
        }
    }
}
