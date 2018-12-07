package com.jbsx.player.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.PlayerUserCase;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.CommentInputDialog;
import com.jbsx.customview.PushFromBottomDialog;
import com.jbsx.customview.recyclerview.CenterLayoutManager;
import com.jbsx.player.DefinitionController;
import com.jbsx.player.DefinitionIjkVideoView;
import com.jbsx.player.adapter.EpisodeLandScapeItemAdapter;
import com.jbsx.player.adapter.EpisodePortraitItemAdapter;
import com.jbsx.player.contact.PlayerContact;
import com.jbsx.player.data.AlbumData;
import com.jbsx.player.data.ConcernData;
import com.jbsx.player.data.PlayerData;
import com.jbsx.player.data.SingleVideoData;
import com.jbsx.player.data.SpecialSingleData;
import com.jbsx.player.interf.IOnDefinitionSwitchListener;
import com.jbsx.player.presenter.PlayerPresenter;
import com.jbsx.player.util.AlbumDetailUtil;
import com.jbsx.player.util.PlayerHelper;
import com.jbsx.player.util.SingleVideoUtil;
import com.jbsx.player.view.VideoCommentsView;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ShareHelper;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.UiTools;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.myinfo.data.UserComments;

import java.util.LinkedHashMap;
import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by lijian on 2018/11/19.
 *
 * 如果有专辑信息则先获取视频列表
 * 获取列表成功后，如果是专辑信息，则获取第一集播放信息。如果是单一视频，则获取该视频播放信息。
 * 获取当前视频的文字描述内容
 * 视频列表中默认选中当前播放的选集
 * 加载当前选集的评论信息
 * 收藏
 * 举报
 * 评论
 */

public class PlayerFragment extends BaseFragment implements PlayerContact.View, FragmentBackHandler {
    /** 视频数据加载状态 */
    /** 即将获取专辑中的视频列表 */
    private final static int WAITING_FOR_VIDEO_OF_ALBUM = 1;

    /** 即将获取某个视频信息 */
    private final static int WAITING_FOR_VIDEO_INFO = 2;

    /** 获取视频信息成功 */
    private final static int LOAD_VIDEO_INFO_SUCCESS = 3;

    public static final String ARGUMENT = "argument";

    private View mRootView;
    private DefinitionIjkVideoView mPlayerView;

    // 视频评论
    private TextView mTvPostComment;

    private NestedScrollView mNestedScrollView;

    /** 竖屏状态下的选集列表 */
    private RecyclerView mRvPorEpisodes;
    private EpisodePortraitItemAdapter mProEpiAdapter;
    private List<Single> mListEpisodes;

    /** 横屏状态下的选集列表 */
    private EpisodeLandScapeItemAdapter mLandEpiAdapter;

    /** 视频评论列表 */
    private VideoCommentsView mVideoCommentsListView;
    private TextView mTvCommentCount;

    /** 评论输入框 */
    private Dialog mInputCommentDialog;

    /** 全0集 */
    private TextView mTvPorEpisodesCountLabel;

    private ImageView mIvFavorite;
    private ImageView mIvShare;


    /** 上游请求的信息 */
    private PlayerData mRequestData;

    /** 专辑视频列表 */
    private AlbumData mAlbumData;

    /** 当前视屏播放进度 */
    private long mSingleCurrentPosition;

    /** 视频清晰度信息 */
    private LinkedHashMap<String, String> mDefinitionList;

    /** 片库详情信息 */
    private SpecialSingleData mSpecialSingleData;

    /**
     * 视屏详情对话框
     */
    private PushFromBottomDialog mSingleDetailDialog;

    private PlayerContact.Presenter mPresenter;

    /** 当前视频的id信息 */
    private String mAlbumId;
    private String mSingleId;

    public PlayerFragment() {
        // Required empty public constructor
        mDefinitionList = new LinkedHashMap<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestData = bundle.getParcelable(ARGUMENT);
        }
    }

    public static PlayerFragment newInstance(PlayerData argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        PlayerFragment contentFragment = new PlayerFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public void createPresenter() {
        PlayerUserCase userCase = new PlayerUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new PlayerPresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.player_fragment, null, false);
        createPresenter();
        initViews();
        initPortraitEpisodesListView();
        initLandScapeEpisodesListView();
        initEvents();

        // 初始状态为加载专辑视频列表
        setStatus(WAITING_FOR_VIDEO_OF_ALBUM);

        return mRootView;
    }

    private void setStatus(int status) {
        Message message = Message.obtain(mStatusHandler);
        message.what = status;
        message.sendToTarget();
    }

    private Handler mStatusHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case WAITING_FOR_VIDEO_OF_ALBUM:
                    handleRequestVideoOfAlbum();
                    break;
                case WAITING_FOR_VIDEO_INFO:
                    handleRequestSingleVideo();
                    break;
                case LOAD_VIDEO_INFO_SUCCESS:
                    handleReadyToPlay();
                    break;
            }
        };
    };

    /**
     * 视频信息加载完毕，准备播放
     */
    private void handleReadyToPlay() {
        removeProgressBar();
        initPlayer();
    }

    private void removeProgressBar() {
        ProgressBarHelper.removeProgressBar(mRootView);
    }

    /**
     * 获取专辑的视频列表
     */
    private void handleRequestVideoOfAlbum() {
        if (hasAlbumInfo()) {
            ProgressBarHelper.addProgressBar(mRootView);
            mPresenter.loadVideoOfAlbum(mRequestData.getAlbumId());
        }
    }

    /**
     * 设置专辑id和选集id，留着全局使用
     *
     * @param albumId
     * @param singleId
     */
    private void setGlobalVideoIds(String albumId, String singleId) {
        mAlbumId = albumId;
        mSingleId = singleId;
    }

    /**
     * 获取当前选集在专辑中的索引
     */
    private int getEpisodeIndexOfAlbum(String singleId) {
        if (mAlbumData != null && mAlbumData.getPayload() != null && mAlbumData.getPayload().getSingles() != null) {
            List<Single> singleList = mAlbumData.getPayload().getSingles();

            if (singleList != null && !singleList.isEmpty()) {
                for(int i = 0; i < singleList.size(); i++) {
                    if (singleList.get(i).getId().equals(singleId)) {
                        return i;
                    }
                }
            }
        }

        return 0;
    }

    /**
     * 获取某个视频
     */
    private void handleRequestSingleVideo() {
        String singleId = null;

        if (isSingle()) {
            // 进来的是单一视频，则请求数据来自于这个视频的信息
            singleId = mRequestData.getSingleId();
        } else if (isAlbum()) {
            // 进来的是专辑，则请求数据来自于专辑列表中的第一个视频
            Single single = getSingleFromAlbum(0);
            if (single != null) {
                singleId = single.getId();
            }
        }

        // 设置当前默认选中的选集
        syncEpisodeSelection(getEpisodeIndexOfAlbum(singleId));

        // 记录全局数据
        setGlobalVideoIds(mRequestData.getAlbumId(), singleId);

        if (!TextUtils.isEmpty(singleId)) {
            // 加载单一视频信息，不同清晰度需要切换接口返回，只能同时调用多次
            mPresenter.loadSingleVideo(singleId, ConstData.VIDEO_DEFINITION_TYPE_STAND);
            mPresenter.loadSingleVideo(singleId, ConstData.VIDEO_DEFINITION_TYPE_HIGH);
        }

        // 加载片库信息
        mPresenter.loadAlbumDetail(mRequestData.getAlbumId(), singleId);

        // 加载评论信息
        initCommentsList(mRequestData.getAlbumId(), singleId);
    }

    /**
     * 同步修改横屏、竖屏选集选中信息
     */
    private void syncEpisodeSelection(int episodeIndex) {
        mProEpiAdapter.setFocusedPosition(episodeIndex);
        mProEpiAdapter.notifyDataSetChanged();

        mLandEpiAdapter.setFocusedPosition(episodeIndex);
        mLandEpiAdapter.notifyDataSetChanged();
    }

    /**
     * 切换选集
     *
     * @param episodeIndex
     */
    private void handleSelectEpisode(int episodeIndex) {
        // 绘制选中信息
        syncEpisodeSelection(episodeIndex);

        Single single = getSingleFromAlbum(episodeIndex);
        if (single != null) {
            mSingleId = single.getId();
        }

        // 重新加载清晰度
        mDefinitionList.clear();
        mPresenter.loadSingleVideo(mSingleId, ConstData.VIDEO_DEFINITION_TYPE_STAND);
        mPresenter.loadSingleVideo(mSingleId, ConstData.VIDEO_DEFINITION_TYPE_HIGH);

        // 加载片库信息
        mPresenter.loadAlbumDetail(mRequestData.getAlbumId(), mSingleId);

        // 加载评论信息
        initCommentsList(mRequestData.getAlbumId(), mSingleId);

        // 清空视屏状态
        mPlayerView.release();
    }

    /**
     * 获得专辑列表中的某个视频信息
     *
     * @param position
     * @return
     */
    private Single getSingleFromAlbum(int position) {
        Single single = null;
        if (mAlbumData != null && mAlbumData.getPayload() != null && mAlbumData.getPayload().getSingles() != null) {
            List<Single> singleList = mAlbumData.getPayload().getSingles();

            if (singleList != null && !singleList.isEmpty() && position >= 0 && position < singleList.size()) {
                return singleList.get(position);
            }
        }

        return single;
    }

    /**
     * 绘制视频列表信息
     *
     * @param albumData
     */
    @Override
    public void drawVideoOfAlbum(AlbumData albumData) {
        // 绘制视频列表信息，同时加载视频信息
        mAlbumData = albumData;

        // 渲染选集列表
        drawBothEpisodeListView();

        setStatus(WAITING_FOR_VIDEO_INFO);
    }



    /**
     * 绘制单一视频信息
     *
     * @param videoData
     */
    @Override
    public void drawSingleVideo(SingleVideoData videoData) {
        if (SingleVideoUtil.isStandDefinition(videoData)) {
            mDefinitionList.put(ConstData.VIDEO_DEFINITION_TYPE_NAME_STAND, SingleVideoUtil.getVideoUrl(videoData));
        } else if (SingleVideoUtil.isHighDefinition(videoData)) {
            mDefinitionList.put(ConstData.VIDEO_DEFINITION_TYPE_NAME_HIGH, SingleVideoUtil.getVideoUrl(videoData));
        }

        // 目前2个清晰度
        if (mDefinitionList.size() == 2) {
            // 异步获取的清晰度，需要排序
            mDefinitionList = PlayerHelper.sortDefinition(mDefinitionList);
            setStatus(LOAD_VIDEO_INFO_SUCCESS);
        }

        // 当前视频播放进度
        mSingleCurrentPosition = SingleVideoUtil.getSecond(videoData);

        // 收藏状态
        mIvFavorite.setVisibility(View.VISIBLE);
        handleDrawConcernStatus(SingleVideoUtil.isFavorite(videoData));
    }

    /**
     * 片库详情
     *
     * @param specialSingleData
     */
    @Override
    public void drawAlbumDetail(SpecialSingleData specialSingleData) {
        mSpecialSingleData = specialSingleData;

        TextView title = mRootView.findViewById(R.id.player_album_detail_title);
        TextView crew = mRootView.findViewById(R.id.player_album_detail_crew);
        TextView count = mRootView.findViewById(R.id.player_album_detail_count);
        TextView summary = mRootView.findViewById(R.id.player_album_detail_summary);

        title.setText(AlbumDetailUtil.getTitle(specialSingleData));
        crew.setText(AlbumDetailUtil.getScrew(specialSingleData));
        count.setText(AlbumDetailUtil.getCount(specialSingleData));
        summary.setText("简介 >");

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示简介框
                handleShowDetailDialog();
            }
        });

        mIvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCencernClick();
            }
        });
    }

    /**
     * 显示专辑详情对话框
     */
    private void handleShowDetailDialog() {
        View dialogView = LayoutInflater.from(MainApplicationLike.getAppContext())
                .inflate(R.layout.single_video_detail_info_view, null);

        TextView title = dialogView.findViewById(R.id.dialog_album_detail_title);
        TextView crew = dialogView.findViewById(R.id.dialog_album_detail_crew);
        TextView summary = dialogView.findViewById(R.id.dialog_album_detail_summary);
        ImageView close = dialogView.findViewById(R.id.dialog_album_detail_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleDetailDialog.dismiss();
            }
        });

        title.setText(AlbumDetailUtil.getTitle(mSpecialSingleData));
        crew.setText(AlbumDetailUtil.getScrew(mSpecialSingleData));
        summary.setText(AlbumDetailUtil.getIntroduce(mSpecialSingleData));

        mSingleDetailDialog = new PushFromBottomDialog(mContext, dialogView);
        mSingleDetailDialog.setSize(WindowManager.LayoutParams.MATCH_PARENT, UiTools.dip2px(350));
        mSingleDetailDialog.show();
    }

    private void initViews() {
        mPlayerView = mRootView.findViewById(R.id.player);
        mNestedScrollView = mRootView.findViewById(R.id.nsv_player_page);
        mRvPorEpisodes = mRootView.findViewById(R.id.rv_player_portrait_episodes);
        mTvPorEpisodesCountLabel = mRootView.findViewById(R.id.tv_portrait_episodes_count_label);
        mTvPostComment = mRootView.findViewById(R.id.tv_add_comment);
        mTvCommentCount = mRootView.findViewById(R.id.tv_comments_label);
        mIvFavorite = mRootView.findViewById(R.id.player_album_detail_favorite);
        mIvShare = mRootView.findViewById(R.id.player_album_detail_share);
    }

    private void initEvents() {
        mTvPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePostComment();
            }
        });

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // 评论数据翻页
                    mVideoCommentsListView.onLoadMore();
                }
            }
        });

        mIvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShareClick();
            }
        });
    }

    /**
     * 一键分享
     */
    private void handleShareClick() {
        ShareHelper.showShare(mContext);
    }

    private void initPlayer() {
        DefinitionController controller = new DefinitionController(mContext);
        controller.setEpisodeAdapter(mLandEpiAdapter);

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

        mPlayerView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int i) {
            }

            @Override
            public void onPlayStateChanged(int i) {
                // 控制播放进度
                if (i == IjkVideoView.STATE_PREPARED) {
                    mPlayerView.seekTo(mSingleCurrentPosition);
                }
            }
        });

        mPlayerView.setOnDefinitionSwitchListener(new IOnDefinitionSwitchListener() {
            @Override
            public void onDefinitionSwitch(long currentPosition) {
                // 切换清晰度后更新播放进度，只记录本地
                mSingleCurrentPosition = currentPosition;
            }
        });

        mPlayerView.setDefinitionVideos(mDefinitionList);
        mPlayerView.setVideoController(controller);
        mPlayerView.setTitle(AlbumDetailUtil.getTitle(mSpecialSingleData));
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

    /**
     * 判断是否为单独一集
     *
     * @return
     */
    private boolean isSingle() {
        return hasAlbumInfo() && !TextUtils.isEmpty(mRequestData.getSingleId());
    }

    /**
     * 是否为专辑
     * singId为空
     *
     * @return
     */
    private boolean isAlbum() {
        return hasAlbumInfo() && TextUtils.isEmpty(mRequestData.getSingleId());
    }

    /**
     * 数据中是否包含专辑信息
     *
     * @return
     */
    private boolean hasAlbumInfo() {
        return mRequestData != null && !TextUtils.isEmpty(mRequestData.getAlbumId());
    }

    /**
     * 选集列表，竖屏
     */
    private void initPortraitEpisodesListView() {
        CenterLayoutManager layoutManager = new CenterLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRvPorEpisodes.setLayoutManager(layoutManager);

        mProEpiAdapter = new EpisodePortraitItemAdapter(mContext, R.layout.protrait_episode_item);
        mProEpiAdapter.setDatas(mListEpisodes);
        mProEpiAdapter.setOnMyItemClickListener(new EpisodePortraitItemAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
                // 点击item的自动居中
                mRvPorEpisodes.smoothScrollToPosition(position);
                handleSelectEpisode(position);
            }
        });

        mRvPorEpisodes.setAdapter(mProEpiAdapter);
    }

    /**
     * 选集列表，横屏
     */
    private void initLandScapeEpisodesListView() {
        mLandEpiAdapter = new EpisodeLandScapeItemAdapter(mContext, R.layout.landscape_episode_item);
        mLandEpiAdapter.setDatas(mListEpisodes);
        mLandEpiAdapter.setOnMyItemClickListener(new EpisodePortraitItemAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
                handleSelectEpisode(position);
            }
        });
    }

    /**
     * 渲染选集列表，竖屏+横屏
     */
    private void drawBothEpisodeListView() {
        if (mAlbumData != null && mAlbumData.getPayload() != null) {
            List<Single> singles = mAlbumData.getPayload().getSingles();
            mProEpiAdapter.addList(singles);
            mLandEpiAdapter.addList(singles);

            mListEpisodes = mProEpiAdapter.getDatas();

            mTvPorEpisodesCountLabel.setText("全" + mListEpisodes.size() + "集");
        }
    }

    /**
     * 视频评论列表
     */
    private void initCommentsList(String albumId, String singleId) {
        UserComments comments = new UserComments();
        comments.setSpecialAlbumId(albumId);
        comments.setSpecialSingleId(singleId);

        mVideoCommentsListView = VideoCommentsView.newInstance(comments);
        mVideoCommentsListView.setCommentCountView(mTvCommentCount);
        mVideoCommentsListView.setNestedScrollingEnabled(false);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.layout_player_comments, mVideoCommentsListView)
                .commitAllowingStateLoss();
    }

    /**
     * 发表评论
     */
    private void handlePostComment() {
        mInputCommentDialog = CommentInputDialog.showInputComment(getActivity(), "",new CommentInputDialog.CommentDialogListener() {
            @Override
            public void onClickPublish(Dialog dialog, EditText input, TextView btn) {
                mPresenter.postComment(mAlbumId, mSingleId, input.getText().toString());
                mInputCommentDialog.dismiss();
            }

            @Override
            public void onShow(int[] inputViewCoordinatesOnScreen) {
            }

            @Override
            public void onDismiss() {

            }
        });
    }

    /**
     * 关注结果
     */
    @Override
    public void drawConcernResult(ConcernData data) {
        boolean isConcerned = (data != null && data.getPayload() != null
                && !TextUtils.isEmpty(data.getPayload().getStatus()));

        handleDrawConcernStatus(isConcerned);
    }

    /**
     * 绘制关注状态
     *
     * @param concerned
     */
    private void handleDrawConcernStatus(boolean concerned) {
        mIvFavorite.setImageResource(concerned ? R.drawable.player_favorite_red : R.drawable.player_favorite_black);
    }

    /**
     * 收藏视频
     */
    private void handleCencernClick() {
        mPresenter.handleConcernVideo(mAlbumId, mSingleId);
    }

    @Override
    public void drawPostSuccess() {
        ShowTools.toast("评论成功");

        // 刷新评论列表
        mVideoCommentsListView.clearAndFresh();
    }

    /**
     * 记录播放时间
     */
    private void recordWatchTime() {
        mPresenter.recordWatchTime(mAlbumId, mSingleId, mPlayerView.getCurrentPosition() + "");
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerView.pause();
        recordWatchTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayerView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerView.release();
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }
}
