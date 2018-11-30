package com.jbsx.player;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.L;
import com.dueeeke.videoplayer.util.WindowUtil;
import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.player.interf.DefinitionMediaPlayerControl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * 清晰度和选集
 *
 * Created by Devlin_n on 2018/4/16.
 */

public class DefinitionController extends StandardVideoController {
    protected TextView multiRate;
    //    private PopupMenu mPopupMenu;
    private PopupWindow mPopupWindow;
    private List<String> mRateStr;
    private List<TextView> mRateItems;
    private LinearLayout mPopLayout;

    private TextView mTvEpisode;
    private List<String> mEpisodeList;

    /** 选集列表 */
    private View mDrawerLayout;
    private View mViewLandEpisodesContainer;
    private RecyclerView mRvLandEpisodes;
    private View mViewPlaceHolder;
    private View mViewEpisodeBg;

    private Animation mAnimationShow;
    private Animation mAnimationHide;
    private ObjectAnimator mAnimationFadeOut;
    private ObjectAnimator  mAnimationFadeIn;

    public DefinitionController(@NonNull Context context) {
        this(context, null);
    }

    public DefinitionController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefinitionController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        initMultiRate();
        initEpisode();
        initEpisodeList();
        initEpisodeAnimation();
    }

    private void initEpisodeAnimation() {
        Context context = MainApplicationLike.getAppContext();

        // 黑色背景淡出
        mAnimationFadeOut = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.animator_alpha_out);
        mAnimationFadeOut.setTarget(mViewEpisodeBg);

        // 黑色背景淡入
        mAnimationFadeIn = (ObjectAnimator)AnimatorInflater.loadAnimator(context, R.animator.animator_alpha_in);
        mAnimationFadeIn.setTarget(mViewEpisodeBg);

        // 分类列表进入
        mAnimationShow = AnimationUtils.loadAnimation(context, R.anim.view_slide_right_to_left_in);
        mAnimationShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mAnimationFadeIn.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // 分类列表退出
        mAnimationHide = AnimationUtils.loadAnimation(context, R.anim.view_slide_right_to_left_out);
        mAnimationHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mAnimationFadeOut.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画完毕后再隐藏界面
                mDrawerLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void playAnimation(boolean show) {
        if (show) {
            mDrawerLayout.setVisibility(View.VISIBLE);
            mViewLandEpisodesContainer.startAnimation(mAnimationShow);
        } else {
            mViewLandEpisodesContainer.startAnimation(mAnimationHide);
        }
    }

    private void initEpisodeList() {
        int itemCount = 3;

        // 点击空白区域关闭选集列表
        mViewPlaceHolder = controllerView.findViewById(R.id.view_place_holder);
        mViewPlaceHolder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideLandScapeEpisodes();
            }
        });

        // 初始化选集列表
        mViewLandEpisodesContainer = controllerView.findViewById(R.id.rv_player_landscape_episodes_container);
        mRvLandEpisodes = controllerView.findViewById(R.id.rv_player_landscape_episodes);
        mViewEpisodeBg = controllerView.findViewById(R.id.layout_episode_animation_bg);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), itemCount);
        mRvLandEpisodes.setLayoutManager(layoutManager);
    }

    public void setEpisodeAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRvLandEpisodes.setAdapter(adapter);
        }
    }

    /**
     * 清晰度选择
     */
    private void initMultiRate() {
        multiRate = controllerView.findViewById(R.id.tv_multi_rate);
        multiRate.setOnClickListener(this);
        mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_rate_pop, this, false);
        mPopupWindow.setContentView(mPopLayout);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setClippingEnabled(false);
    }

    /**
     * 选集
     */
    private void initEpisode() {
        mDrawerLayout = controllerView.findViewById(R.id.dl_container);
        mTvEpisode = controllerView.findViewById(R.id.tv_select_episode);
        mTvEpisode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEpisode();
            }
        });
    }

    /**
     * 显示选集列表
     */
    private void showLandScapeEpisodes() {
        playAnimation(true);
    }

    private void hideLandScapeEpisodes() {
        playAnimation(false);
    }

    private void selectEpisode() {
        // 显示选集列表
        showLandScapeEpisodes();
    }

    /**
     * 使用自定义视频layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.dkplayer_layout_custom_controller;
    }

    @Override
    public void setPlayerState(int playerState) {
        super.setPlayerState(playerState);
        switch (playerState) {
            case IjkVideoView.PLAYER_NORMAL:
                multiRate.setVisibility(GONE);
                mTvEpisode.setVisibility(View.GONE);
                hideLandScapeEpisodes();
                break;
            case IjkVideoView.PLAYER_FULL_SCREEN:
                multiRate.setVisibility(VISIBLE);
                mTvEpisode.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.tv_multi_rate) {
            showRateMenu();
        }
    }

    @Override
    public void hide() {
        super.hide();
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void showRateMenu() {
        mPopLayout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mPopupWindow.showAsDropDown(multiRate, -((mPopLayout.getMeasuredWidth() - multiRate.getMeasuredWidth()) / 2),
                -(mPopLayout.getMeasuredHeight() + multiRate.getMeasuredHeight() + WindowUtil.dp2px(getContext(), 10)));
    }

    private int currentIndex;

    @Override
    protected int setProgress() {
        if (multiRate != null && TextUtils.isEmpty(multiRate.getText())) {
            L.d("multiRate");
            LinkedHashMap<String, String> multiRateData = ((DefinitionMediaPlayerControl) mediaPlayer).getDefinitionData();
            if (multiRateData == null) return super.setProgress();
            mRateStr = new ArrayList<>();
            mRateItems = new ArrayList<>();
            int index = 0;
            ListIterator<Map.Entry<String, String>> iterator = new ArrayList<>(multiRateData.entrySet()).listIterator(multiRateData.size());
            while (iterator.hasPrevious()) {//反向遍历
                Map.Entry<String, String> entry = iterator.previous();
                mRateStr.add(entry.getKey());
                TextView rateItem = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_rate_item, null);
                rateItem.setText(entry.getKey());
                rateItem.setTag(index);
                rateItem.setOnClickListener(rateOnClickListener);
                mPopLayout.addView(rateItem);
                mRateItems.add(rateItem);
                index++;
            }
            mRateItems.get(index - 1).setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
            multiRate.setText(mRateStr.get(index - 1));
            currentIndex = index - 1;
        }
        return super.setProgress();
    }

    private OnClickListener rateOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            if (currentIndex == index) return;
            mRateItems.get(currentIndex).setTextColor(Color.BLACK);
            mRateItems.get(index).setTextColor(ContextCompat.getColor(getContext(), R.color.theme_color));
            multiRate.setText(mRateStr.get(index));
            ((DefinitionMediaPlayerControl) mediaPlayer).switchDefinition(mRateStr.get(index));
            mPopupWindow.dismiss();
            hide();
            currentIndex = index;
        }
    };
}
