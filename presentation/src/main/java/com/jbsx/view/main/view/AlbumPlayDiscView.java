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

import java.util.List;

/**
 * 盘面
 */
public class AlbumPlayDiscView extends FrameLayout {
    private View mRootView;
    private TextView mTvName;
    private LinearLayout mLayoutDiscContainer;

    private boolean mIsPlaying;

    public AlbumPlayDiscView(Context context) {
        this(context, null, 0);
    }

    public AlbumPlayDiscView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumPlayDiscView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initEvent();
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.album_player_disc, null);
        mTvName = mRootView.findViewById(R.id.tv_album_play_disc_name);
        mLayoutDiscContainer = mRootView.findViewById(R.id.view_album_play_disc_container);
        addView(mRootView);
    }

    private void initEvent() {

    }

    public void setData(List<AlbumDetailData.SongInfo> songList, String name) {
        if (songList != null) {
            mTvName.setText(name);
            for (AlbumDetailData.SongInfo songInfo : songList) {
                View songItemView = createSongItemView(songInfo);
                if (songItemView != null) {
                    mLayoutDiscContainer.addView(songItemView);
                }
            }
        }
    }

    private View createSongItemView(AlbumDetailData.SongInfo songInfo) {
        AlbumPlayItem itemView = new AlbumPlayItem(getContext());
        itemView.setData(songInfo);
        return itemView;
    }
}
