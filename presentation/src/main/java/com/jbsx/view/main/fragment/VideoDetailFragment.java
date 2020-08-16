package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.ViewUtils;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.entity.GalleryData;
import com.jbsx.view.main.entity.RepertoryData;
import com.jbsx.view.main.entity.VideoDetailData;
import com.jbsx.view.main.util.PageUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 视频详情介绍
 */
public class VideoDetailFragment extends BaseFragment {
    private View mRootView;
    private View mViewLayout;
    private View mViewLoading;
    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvDirector;
    private TextView mTvActor;
    private TextView mTvDuration;
    private TextView mTvPublishDate;
    private TextView mTvSummary;
    private View mBtnPlay;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    private MainPageUserCase mUserCase;

    public VideoDetailFragment() {
        // Required empty public constructor
    }

    public static VideoDetailFragment newInstance(String naviId, String naviType, Integer pageType,
                                                String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        VideoDetailFragment contentFragment = new VideoDetailFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mNaviId = bundle.getString(AppConstData.INTENT_KEY_NAVI_ID);
            mNaviType = bundle.getString(AppConstData.INTENT_KEY_NAVI_TYPE);
            mPageType = bundle.getInt(AppConstData.INTENT_KEY_PAGE_TYPE);
            mRequestParams = bundle.getString(AppConstData.INTENT_KEY_REQUEST_PARAMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.video_detail_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();
        loadData();

        return mRootView;
    }

    public void createPresenter() {
        mUserCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    private void initViews() {
        mViewLayout = mRootView.findViewById(R.id.layout_video_detail_root);
        mViewLoading = mRootView.findViewById(R.id.view_video_detail_loading);
        mIvIcon = mRootView.findViewById(R.id.iv_video_detail_icon);
        mTvName = mRootView.findViewById(R.id.iv_video_detail_name);
        mTvDirector = mRootView.findViewById(R.id.iv_video_detail_director);
        mTvActor = mRootView.findViewById(R.id.iv_video_detail_actor);
        mTvDuration = mRootView.findViewById(R.id.iv_video_detail_video_time);
        mTvPublishDate = mRootView.findViewById(R.id.iv_video_detail_play_date);
        mTvSummary = mRootView.findViewById(R.id.iv_video_detail_summary);
        mBtnPlay = mRootView.findViewById(R.id.btn_video_detail_play);
    }

    private void initEvents() {
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType, mPageType, getVideoUrl()));
            }
        });
    }

    private String getVideoUrl() {
        return ConstData.VIDEO_HOST + "/terminal/views/videos.html?" + "deviceId=" + ConstData.DEVICE_ID
                + "&orgCode=" + ConstData.ORG_CODE + "&videoCode=" + mRequestParams;
    }

    private void loadData() {
        ProgressBarHelper.addProgressBar(mViewLoading);
        mUserCase.requestVideoDetailInfo(mRequestParams, new BaseRequestCallback() {
            @Override
            public void onRequestFailed(BaseDomainData data) {
                handleLoadFailed(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleLoadSuccessful(data);
            }

            @Override
            public void onNetError() {
                drawNetError();
            }
        });
    }

    private void handleLoadSuccessful(String data) {
        ProgressBarHelper.removeProgressBar(mViewLoading);
        VideoDetailData parseData = ParseUtil.parseData(data, VideoDetailData.class);
        drawVideoDetail(parseData.getBody());
    }

    private void drawVideoDetail(RepertoryData.FeedItem detail) {
        mRequestParams = (detail != null ? detail.getVideoCode() : "");
        if (detail != null && detail.getMetadata() != null) {
            ImageLoader.displayImage(detail.getVideoPreview(), mIvIcon);
            ViewUtils.drawText(mTvName, detail.getVideoName());
            ViewUtils.drawText(mTvDirector, ViewUtils.getBoldText("导演："
                    + detail.getMetadata().getDirector(), 0, 3));
            ViewUtils.drawText(mTvActor, ViewUtils.getBoldText("演员："
                    + detail.getMetadata().getActor(), 0, 3));
            ViewUtils.drawText(mTvDuration, ViewUtils.getBoldText("片长："
                    + detail.getMetadata().getVideoTime(), 0, 3));
            ViewUtils.drawText(mTvPublishDate, ViewUtils.getBoldText("上映时间："
                    + detail.getMetadata().getPlayDate(), 0, 5));
            ViewUtils.drawText(mTvSummary, Html.fromHtml(detail.getMetadata().getIntro()));
        }
    }

    private void handleLoadFailed(BaseDomainData data) {
        ProgressBarHelper.removeProgressBar(mViewLoading);
        MessageTools.showErrorMessage(data);
        String errorMessage = data.getMsg();
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = ErroBarHelper.ERRO_TYPE_NET_NAME;
        }

        ReloadBarHelper.addReloadBar(mViewLayout, errorMessage, new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
    }

    public void drawNetError() {
        ProgressBarHelper.removeProgressBar(mViewLoading);
        ErroBarHelper.addErroBar(mViewLayout, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
            @Override
            public void run() {
                ErroBarHelper.removeErroBar(mViewLayout);
                loadData();
            }
        });
    }

}
