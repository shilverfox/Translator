package com.jbsx.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.jbsx.player.interf.DefinitionMediaPlayerControl;
import com.jbsx.player.interf.IOnDefinitionSwitchListener;
import com.jbsx.player.interf.ListMediaPlayerControl;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 清晰度切换
 * Created by xinyu on 2018/4/16.
 */

public class DefinitionIjkVideoView extends IjkVideoView
        implements DefinitionMediaPlayerControl, ListMediaPlayerControl {
    private LinkedHashMap<String, String> mDefinitionMap;
    private String mCurrentDefinition;

    protected List<VideoModel> mVideoModels;//列表播放数据
    protected int mCurrentVideoPosition = 0;//列表播放时当前播放视频的在List中的位置

    /** 清晰度切换后及时更新播放进度 */
    private IOnDefinitionSwitchListener mOnDefinitionSwitch;

    public DefinitionIjkVideoView(@NonNull Context context) {
        super(context);
    }

    public DefinitionIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DefinitionIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LinkedHashMap<String, String> getDefinitionData() {
        return mDefinitionMap;
    }

    @Override
    public void switchDefinition(String definition) {
        String url = mDefinitionMap.get(definition);
        if (definition.equals(mCurrentDefinition)) {
            return;
        }

        if (mOnDefinitionSwitch != null) {
            mOnDefinitionSwitch.onDefinitionSwitch(getCurrentPosition());
        }

        mCurrentUrl = url;
        addDisplay();
        getCurrentPosition();
        startPrepare(true);
        mCurrentDefinition = definition;
    }

    public void setOnDefinitionSwitchListener(IOnDefinitionSwitchListener listener) {
        mOnDefinitionSwitch = listener;
    }

    public void setDefinitionVideos(LinkedHashMap<String, String> videos) {
        this.mDefinitionMap = videos;
        this.mCurrentUrl = getValueFromLinkedMap(videos, 0);
    }

    public static String getValueFromLinkedMap(LinkedHashMap<String, String> map, int index) {
        int currentIndex = 0;
        for (String key : map.keySet()) {
            if (currentIndex == index) {
                return map.get(key);
            }
            currentIndex++;
        }
        return null;
    }

    /**
     * 播放下一条视频
     */
    private void playNext() {
        VideoModel videoModel = mVideoModels.get(mCurrentVideoPosition);
        if (videoModel != null) {
            mCurrentUrl = videoModel.url;
            mCurrentTitle = videoModel.title;
            mPlayerConfig.isCache = videoModel.isCache;
            mCurrentPosition = 0;
            setVideoController(videoModel.controller);
        }
    }

    /**
     * 设置一个列表的视频
     */
    public void setVideos(List<VideoModel> videoModels) {
        this.mVideoModels = videoModels;
        playNext();
    }

    /**
     * 播放下一条视频，可用于跳过广告
     */
    @Override
    public void skipToNext() {
        mCurrentVideoPosition++;
        if (mVideoModels != null && mVideoModels.size() > 1) {
            if (mCurrentVideoPosition >= mVideoModels.size()) return;
            playNext();
            addDisplay();
            startPrepare(true);
        }
    }

}
