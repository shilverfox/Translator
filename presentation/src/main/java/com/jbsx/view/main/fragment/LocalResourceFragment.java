package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.DotImageIndicator;
import com.jbsx.customview.gallery.CoverFlowLayoutManger;
import com.jbsx.customview.gallery.RecyclerCoverFlow;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.adapter.GalleryAdapter;
import com.jbsx.view.main.entity.NavigationData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地资源
 */
public class LocalResourceFragment extends BaseFragment {
    /** 本地资源类型：图片 */
    public final static String KEY_SOURCE_TYPE_PICTURE = "picture";

    /** 本地资源类型：新闻 */
    public final static String KEY_SOURCE_TYPE_NEWS = "news";

    /** 本地资源类型：视频 */
    public final static String KEY_SOURCE_TYPE_VIDEO = "video";

    private View mRootView;
    private ViewGroup mContainerView;
    private RecyclerCoverFlow mList;
    private DotImageIndicator mImageIndicator;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    private MainPageUserCase mUserCase;
    private List<NavigationData.ClassifyEntity> mGalleryData = new ArrayList<>();;
    private GalleryAdapter mAdapter;

    public LocalResourceFragment() {
        // Required empty public constructor
    }

    public static LocalResourceFragment newInstance(String naviId, String naviType, Integer pageType,
                                                    String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        LocalResourceFragment contentFragment = new LocalResourceFragment();
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
        mRootView = inflater.inflate(R.layout.local_resource_fragment, null, false);
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
        handleLoadSuccessful("");
    }

    private NavigationData.ClassifyEntity createLocalData(String url, String name, String key) {
        NavigationData navigationData = new NavigationData();
        NavigationData.ClassifyEntity entity = navigationData.new ClassifyEntity();
        entity.setClassifyCode(key);
        entity.setClassifyPreview(url);
        entity.setClassifyName(name);
        return entity;
    }

    private void handleLoadSuccessful(String data) {
        mGalleryData.add(createLocalData("", "本地图集", KEY_SOURCE_TYPE_PICTURE));
        mGalleryData.add(createLocalData("", "本地视频", KEY_SOURCE_TYPE_VIDEO));
        mGalleryData.add(createLocalData("", "新闻资讯", KEY_SOURCE_TYPE_NEWS));
        mAdapter.setData(mGalleryData);
        mAdapter.notifyDataSetChanged();
        handleGalleryAutoFocus();
    }

    /**
     * 自动定位到某个item
     */
    private void handleGalleryAutoFocus() {
        int selectPosition = mGalleryData.size() / 2;
        mList.setSelect(selectPosition);
        mImageIndicator.updateImageDotStatus(selectPosition);
    }

    private void initViews() {
        mContainerView = mRootView.findViewById(R.id.view_local_root);
        mImageIndicator = mRootView.findViewById(R.id.tv_local_indicator);
        mList = mRootView.findViewById(R.id.tv_local_info);
        mList.setGreyItem(true); //设置灰度渐变
        mAdapter = new GalleryAdapter(mContext, new GalleryAdapter.OnGalleryItemClick() {
            @Override
            public void onItemClick(String classifyCode, boolean isHasChildren) {
                handleItemClick(classifyCode);
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

    private void handleItemClick(String key) {
        String params = "";
        Integer pageType = AppConstData.PAGE_TYPE_LOCAL_NEWS;
        if (KEY_SOURCE_TYPE_NEWS.equals(key)) {
            params = ConstData.HOST + "/terminal/views/news.html";
            pageType = AppConstData.PAGE_TYPE_LOCAL_NEWS;
        } else if (KEY_SOURCE_TYPE_VIDEO.equals(key)) {
            params = key;
            pageType = AppConstData.PAGE_TYPE_LOCAL_VIDEO_FEED;
        } else if (KEY_SOURCE_TYPE_PICTURE.equals(key)) {
            params = key;
            pageType = AppConstData.PAGE_TYPE_LOCAL_PICTURE_FEED;
        }
        EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType, pageType, params));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
