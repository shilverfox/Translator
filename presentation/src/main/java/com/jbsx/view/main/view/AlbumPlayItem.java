package com.jbsx.view.main.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.view.main.entity.AlbumDetailData;

public class AlbumPlayItem extends FrameLayout {
    private View mRootView;
    private TextView mTvName;
    private TextView mTvDuration;
    private ImageView mIvPlay;
    private ImageView mIvLike;

    private boolean mIsPlaying;

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
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.album_player_item, null);
        mTvName = mRootView.findViewById(R.id.tv_album_play_item_name);
        mIvPlay = mRootView.findViewById(R.id.iv_album_play_item_play);
        mIvLike = mRootView.findViewById(R.id.iv_album_play_item_like);
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
        if (songInfo != null) {
            mTvName.setText(songInfo.getSongName());
            mTvDuration.setText(songInfo.getSongTime());
        }
    }

    private void handlePlay() {
        mIsPlaying = !mIsPlaying;
    }
}
