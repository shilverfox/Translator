package com.jbsx.view.main.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.view.main.entity.AlbumDetailData;

import java.io.IOException;

public class AlbumPlayItem extends FrameLayout {
    private View mRootView;
    private TextView mTvName;
    private TextView mTvDuration;
    private ImageView mIvPlay;
    private ImageView mIvLike;

    private MediaPlayer mediaPlayer;

    private boolean mIsPlaying;
    private AlbumDetailData.SongInfo mSongInfo;

    public AlbumPlayItem(Context context) {
        this(context, null, 0);
    }

    public AlbumPlayItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumPlayItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initEvent();
        initPlayer();
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.album_player_item, null);
        mTvName = mRootView.findViewById(R.id.tv_album_play_item_name);
        mIvPlay = mRootView.findViewById(R.id.iv_album_play_item_play);
        mIvLike = mRootView.findViewById(R.id.iv_album_play_item_like);
        mTvDuration = mRootView.findViewById(R.id.tv_album_play_item_duration);
        addView(mRootView);
    }

    private void initPlayer() {
        mediaPlayer = new MediaPlayer();
    }

    private void initEvent() {
        mIvPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlay();
            }
        });

        mIvLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setData(AlbumDetailData.SongInfo songInfo) {
        mSongInfo = songInfo;
        if (songInfo != null) {
            mTvName.setText(songInfo.getSongName());
            mTvDuration.setText(songInfo.getSongTime());
        }
    }

    private void handlePlay() {
        mIsPlaying = !mIsPlaying;
        if (mIsPlaying) {
            pauseMusice();
        } else {
            playMusic(mSongInfo.getSongFilePath());
        }
    }

    private void playMusic(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start(); // 准备好了就播放
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseMusice() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
