package com.jbsx.view.main.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.customview.JDProgressBar;
import com.jbsx.view.main.AudioPlayer;
import com.jbsx.view.main.entity.AlbumDetailData;

public class AlbumPlayItemView extends FrameLayout {
    private View mRootView;
    private TextView mTvName;
    private TextView mTvDuration;
    private ImageView mIvPlay;
    private ImageView mIvLike;
    private JDProgressBar mPbBar;

    private boolean mIsPlaying;
    private AlbumDetailData.SongInfo mSongInfo;

    public AlbumPlayItemView(Context context) {
        this(context, null, 0);
    }

    public AlbumPlayItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumPlayItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initEvent();
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.album_player_item, null);
        mTvName = mRootView.findViewById(R.id.tv_album_play_item_name);
        mIvPlay = mRootView.findViewById(R.id.iv_album_play_item_play);
        mIvLike = mRootView.findViewById(R.id.iv_album_play_item_like);
        mPbBar = mRootView.findViewById(R.id.pb_album_play_item_play);
        mTvDuration = mRootView.findViewById(R.id.tv_album_play_item_duration);
        addView(mRootView);
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
        if (mIsPlaying) {
            AudioPlayer.getInstance().pauseMusic();
        } else {
            AudioPlayer.getInstance().playMusic(mSongInfo.getSongFilePath(), mPbBar);
        }
        mIsPlaying = !mIsPlaying;
    }
}
