package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
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
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.view.main.entity.GalleryData;
import com.jbsx.view.main.entity.VideoDetailData;

/**
 * 视频详情介绍
 */
public class VideoDetailFragment extends BaseFragment {
    private View mRootView;
    private View mViewLayout;

    private String mRequestParams;
    private String mNaviType;
    private String mPageType;
    private String mNaviId;

    private MainPageUserCase mUserCase;

    public VideoDetailFragment() {
        // Required empty public constructor
    }

    public static VideoDetailFragment newInstance(String naviId, String naviType, String pageType,
                                                String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putString(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
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
            mPageType = bundle.getString(AppConstData.INTENT_KEY_PAGE_TYPE);
            mRequestParams = bundle.getString(AppConstData.INTENT_KEY_REQUEST_PARAMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.video_detail_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();

        return mRootView;
    }

    public void createPresenter() {
        mUserCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    private void initViews() {
        mViewLayout = mRootView.findViewById(R.id.layout_video_detail_root);
    }

    private void initEvents() {

    }

    private void loadData() {
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
        VideoDetailData parseData = ParseUtil.parseData(data, VideoDetailData.class);
    }

    private void handleLoadFailed(BaseDomainData data) {
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
        ErroBarHelper.addErroBar(mViewLayout, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
            @Override
            public void run() {
                ErroBarHelper.removeErroBar(mViewLayout);
                loadData();
            }
        });
    }

}
