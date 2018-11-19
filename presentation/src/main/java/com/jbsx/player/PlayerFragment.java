package com.jbsx.player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.view.myinfo.data.UserComments;

import java.util.LinkedHashMap;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by lijian on 2018/11/19.
 */

public class PlayerFragment extends BaseFragment {
    public static final String ARGUMENT = "argument";

    private View mRootView;
    private DefinitionIjkVideoView mPlayerView;

    private UserComments mRequestData;

    public PlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestData = bundle.getParcelable(ARGUMENT);
        }
    }

    public static PlayerFragment newInstance(UserComments argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        PlayerFragment contentFragment = new PlayerFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.player_fragment, null, false);
        initViews();
        initEvents();
        initPlayer();

        return mRootView;
    }

    private void initViews() {
        mPlayerView = mRootView.findViewById(R.id.player);
    }

    private void initEvents() {

    }

    private void initPlayer() {
        DefinitionController controller = new DefinitionController(mContext);
        mPlayerView.setPlayerConfig(new PlayerConfig.Builder()
                .setCustomMediaPlayer(new IjkPlayer(mContext) {
                    @Override
                    public void setOptions() {
                        //精准seek
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
                    }
                })
                .autoRotate()//自动旋转屏幕
                .build());

        LinkedHashMap<String, String> videos = new LinkedHashMap<>();
        videos.put("标清", "http://mov.bn.netease.com/open-movie/nos/flv/2017/07/24/SCP786QON_sd.flv");
        videos.put("高清", "http://mov.bn.netease.com/open-movie/nos/flv/2017/07/24/SCP786QON_hd.flv");
        videos.put("超清", "http://mov.bn.netease.com/open-movie/nos/flv/2017/07/24/SCP786QON_shd.flv");
        mPlayerView.setDefinitionVideos(videos);
        mPlayerView.setVideoController(controller);
        mPlayerView.setTitle("韩雪：积极的悲观主义者");
        mPlayerView.start();

//        // 高级设置（可选，须在start()之前调用方可生效）
//        PlayerConfig playerConfig = new PlayerConfig.Builder()
//                .enableCache() //启用边播边缓存功能
//                .autoRotate() //启用重力感应自动进入/退出全屏功能
//                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
//                .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
//                .savingProgress() //保存播放进度
//                .disableAudioFocus() //关闭AudioFocusChange监听
//                .setLooping() //循环播放当前正在播放的视频
//                .build();
//        mPlayerView.setPlayerConfig(playerConfig);
//        mPlayerView.start();
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_comment_detail_list, fragment).commitAllowingStateLoss();
        }
    }
}
