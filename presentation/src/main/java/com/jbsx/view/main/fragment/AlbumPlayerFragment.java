package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.utils.ViewUtils;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.AlbumDetailData;
import com.jbsx.view.main.entity.GalleryData;
import com.jbsx.view.main.entity.RepertoryData;
import com.jbsx.view.main.view.AlbumPlayDiscView;

import java.util.List;

/**
 * 专辑播放页
 */
public class AlbumPlayerFragment extends BaseFragment {
    private View mRootView;
    private View mContainerView;
    private View mViewLoading;
    private TextView mTvAlbumName;
    private TextView mTvAlbumCompany;
    private TextView mTvAlbumType;
    private TextView mTvAlbumActor;
    private ImageView mIvAlbumCover;
    private LinearLayout mDiscContainer;
    private View mDiscParent;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    private MainPageUserCase mUserCase;
    private AlbumDetailData mAlbumDetailData;

    public AlbumPlayerFragment() {
        // Required empty public constructor
    }

    public static AlbumPlayerFragment newInstance(String naviId, String naviType, Integer pageType,
                                              String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        AlbumPlayerFragment contentFragment = new AlbumPlayerFragment();
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
        mRootView = inflater.inflate(R.layout.album_player_fragment, null, false);
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
        mContainerView = mRootView.findViewById(R.id.view_album_player_root);
        mViewLoading = mRootView.findViewById(R.id.view_album_player_loading);
        mIvAlbumCover = mRootView.findViewById(R.id.iv_album_play_cover);
        mTvAlbumName = mRootView.findViewById(R.id.tv_album_play_name);
        mTvAlbumCompany = mRootView.findViewById(R.id.tv_album_play_company);
        mTvAlbumType = mRootView.findViewById(R.id.tv_album_play_type);
        mTvAlbumActor = mRootView.findViewById(R.id.tv_album_play_actor);
        mDiscParent = mRootView.findViewById(R.id.iv_album_play_list);
        mDiscParent.setVisibility(View.INVISIBLE);
        mDiscContainer = mRootView.findViewById(R.id.view_album_play_fragment_disc_container);
    }

    private void initEvents() {

    }

    private void loadData() {
        ProgressBarHelper.addProgressBar(mViewLoading);
        mUserCase.requestAlbumDetailInfo(mRequestParams, new BaseRequestCallback() {
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
        mDiscParent.setVisibility(View.VISIBLE);
        AlbumDetailData parseData = ParseUtil.parseData(data, AlbumDetailData.class);
        if (parseData != null && parseData.getBody() != null) {
            handleAlbumInfo(parseData);
            handleAlbumDisc(parseData);
        }
    }

    /**
     * 专辑基本信息
     * @param parseData
     */
    private void handleAlbumInfo(AlbumDetailData parseData) {
        ViewUtils.drawText(mTvAlbumName, parseData.getBody().getAlbumName());
        RepertoryData.MetaData metaData = parseData.getBody().getMetadata();
        ImageLoader.displayImage(parseData.getBody().getPreview(), mIvAlbumCover);
        if (metaData != null) {
            ViewUtils.drawText(mTvAlbumCompany, metaData.getDirector());
            ViewUtils.drawText(mTvAlbumType, metaData.getAlbumType());
            ViewUtils.drawText(mTvAlbumActor, metaData.getActor());
        }
    }

    /**
     * 专辑中歌曲列表
     *
     * @param parseData
     */
    private void handleAlbumDisc(AlbumDetailData parseData) {
        View sideA = createDiscSideView(parseData.getBody().getSideA(), "A-曲目列表");
        if (sideA != null) {
            mDiscContainer.addView(sideA);
        }

        View sideB = createDiscSideView(parseData.getBody().getSideB(), "B-曲目列表");
        if (sideB != null) {
            mDiscContainer.addView(sideB);
        }
    }

    private View createDiscSideView(List<AlbumDetailData.SongInfo> songList, String name) {
        if (songList != null && songList.size() > 0) {
            AlbumPlayDiscView discView = new AlbumPlayDiscView(mContext);
            discView.setData(songList, name);
            return discView;
        } else {
            return null;
        }
    }

    private void handleLoadFailed(BaseDomainData data) {
        ProgressBarHelper.removeProgressBar(mViewLoading);
        MessageTools.showErrorMessage(data);
        String errorMessage = data.getMsg();
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = ErroBarHelper.ERRO_TYPE_NET_NAME;
        }

        ReloadBarHelper.addReloadBar(mContainerView, errorMessage, new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
    }

    public void drawNetError() {
        ProgressBarHelper.removeProgressBar(mViewLoading);
        ErroBarHelper.addErroBar(mContainerView, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
            @Override
            public void run() {
                ErroBarHelper.removeErroBar(mContainerView);
                loadData();
            }
        });
    }
}
