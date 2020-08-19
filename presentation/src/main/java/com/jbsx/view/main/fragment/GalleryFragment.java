package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.DotImageIndicator;
import com.jbsx.customview.gallery.CoverFlowLayoutManger;
import com.jbsx.customview.gallery.RecyclerCoverFlow;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.adapter.GalleryAdapter;
import com.jbsx.view.main.entity.GalleryData;
import com.jbsx.view.main.entity.NavigationData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 画廊样式
 */
public class GalleryFragment extends BaseFragment {
    private View mRootView;
    private ViewGroup mContainerView;
    private RecyclerCoverFlow mList;
    private DotImageIndicator mImageIndicator;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    private MainPageUserCase mUserCase;
    private List<NavigationData.ClassifyEntity> mGalleryData;
    private GalleryAdapter mAdapter;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String naviId, String naviType, Integer pageType,
                                              String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        GalleryFragment contentFragment = new GalleryFragment();
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
        mRootView = inflater.inflate(R.layout.gallery_fragment, null, false);
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

    private void loadData() {
        ProgressBarHelper.addProgressBar(mContainerView);
        mUserCase.requestGalleryInfo(mRequestParams, new BaseRequestCallback() {
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
        ProgressBarHelper.removeProgressBar(mContainerView);
        GalleryData parseData = ParseUtil.parseData(data, GalleryData.class);
        mGalleryData = parseData.getBody();
        if (mGalleryData == null || mGalleryData.size() == 0) {
            // 数据列表为空
            BaseDomainData emptyData = new BaseDomainData();
            emptyData.setMsg("无数据");
            handleLoadFailed(emptyData);
        } else {
            mImageIndicator.initImageDot(mGalleryData.size());
            mAdapter.setData(mGalleryData);
            mAdapter.notifyDataSetChanged();
            handleGalleryAutoFocus();
        }
    }

    /**
     * 自动定位到某个item
     */
    private void handleGalleryAutoFocus() {
        int selectPosition = mGalleryData.size() / 2;
        mList.setSelect(selectPosition);
        mImageIndicator.updateImageDotStatus(selectPosition);
    }

    private void handleLoadFailed(BaseDomainData data) {
        ProgressBarHelper.removeProgressBar(mContainerView);
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
        ProgressBarHelper.removeProgressBar(mContainerView);
        ErroBarHelper.addErroBar(mContainerView, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
            @Override
            public void run() {
                ErroBarHelper.removeErroBar(mContainerView);
                loadData();
            }
        });
    }

    private void initViews() {
        mContainerView = mRootView.findViewById(R.id.view_gallery_root);
        mList = mRootView.findViewById(R.id.tv_gallery_info);
//        mList.setFlatFlow(true); //平面滚动
        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
//        mList.setLoop(); //循环滚动，注：循环滚动模式暂不支持平滑滚动
        mImageIndicator = mRootView.findViewById(R.id.tv_gallery_indicator);
        mAdapter = new GalleryAdapter(mContext, new GalleryAdapter.OnGalleryItemClick() {
            @Override
            public void onItemClick(String classifyCode, boolean isHasChildren) {
                EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType, mPageType,
                        classifyCode, isHasChildren));
            }
        });
        mList.setAdapter(mAdapter);
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                mImageIndicator.updateImageDotStatus(position);
            }
        });
    }

    private void initEvents() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            loadData();
        }
    }
}