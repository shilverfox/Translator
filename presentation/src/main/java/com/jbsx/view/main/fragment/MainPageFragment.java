package com.jbsx.view.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MainPageUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.recyclerview.LoadingFooter;
import com.jbsx.data.AppConstData;
import com.jbsx.player.util.PlayerHelper;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.LogTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.utils.UiTools;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.adapter.CelebrityIconItemAdapter;
import com.jbsx.view.main.adapter.VideoItemAdapter;
import com.jbsx.view.main.contact.MainPageContact;
import com.jbsx.view.main.entity.Album;
import com.jbsx.view.main.entity.HostData;
import com.jbsx.view.main.entity.MainPageData;
import com.jbsx.view.main.entity.SpecialAlbumData;
import com.jbsx.view.main.entity.SpecialAlbums;
import com.jbsx.view.main.presenter.MainPagePresenter;
import com.jbsx.view.main.util.PageUtils;
import com.jbsx.view.main.view.TabItemDecoration;
import com.jbsx.view.web.WebHelper;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.refresh.ProgressStyle;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li Jian
 */

public class MainPageFragment extends BaseFragment implements MainPageContact.View {
    private final static String TAG = "MainPageFragment";

    private View mRootView;
    private ViewGroup mViewContainer;
    private Banner mViewBanner;
    private RecyclerView mRvHostList;
    private TextView mTvRecommendTitle;
    private ViewGroup mLayoutHost;
    private ViewGroup mLayoutRecomment;
    private XRecyclerView mRvRecommendList;
    private VideoItemAdapter mAdapterAlbum;
    private List<SpecialAlbums> mListAlbum = new ArrayList<>();
    private LoadingFooter mFooterLoadingMore;
    private NestedScrollView mNsvRoot;

    /** 名家列表 */
    private CelebrityIconItemAdapter mAdapterCelebrity;
    private List<MainPageData.HomeRecommendEntity> mListCelebrity = new ArrayList<>();

    private MainPageContact.Presenter mPresenter;

    private MainPageData mData;

    /** 当前页码 */
    private int mCurrentPage = 1;
    private boolean mHasNextPage = true;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance(String naviId, String naviType, Integer pageType,
                                               String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        MainPageFragment contentFragment = new MainPageFragment();
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
        mRootView = inflater.inflate(R.layout.main_page_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();
        loadData();
//        calculateBannerHeight();

        return mRootView;
    }

    @Override
    public void createPresenter() {
        MainPageUserCase userCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new MainPagePresenter(this, userCase);
    }

    /**
     * 加载网络数据
     */
    private void loadData() {
        MainApplicationLike.getInstance().getHanlder().post(new Runnable() {
            @Override
            public void run() {
                loadMainPageInfo();
                loadCelebrities();
                loadSpecialAlbum(mCurrentPage);
            }
        });
    }

    /**
     * Banner点击
     *
     * @param bannerInfo
     */
    private void handleBannerClick(MainPageData.HomeNewsEntity bannerInfo) {
        if (bannerInfo != null) {
//            String title = bannerInfo.getNewsTitle();
//            title = (TextUtils.isEmpty(title) ? "详情" : title);
//            WebHelper.openWeb(mContext, bannerInfo.getSource(), title);
            EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType,
                    AppConstData.PAGE_TYPE_LOCAL_NEWS, PageUtils.getNewsUrl(bannerInfo.getNewsCode())));
        }
    }

    private void initEvents() {
        mViewBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mData != null && mData.getBody() != null
                        && mData.getBody().getNewsList() != null) {
                    handleBannerClick(mData.getBody().getNewsList().get(position));
                }
            }
        });

        mViewBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRvRecommendList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (mHasNextPage) {
                    loadSpecialAlbum(++mCurrentPage);
                }
            }
        });

        mNsvRoot.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    LogTools.e(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    LogTools.e(TAG, "Scroll UP");
                }

                if (scrollY == 0) {
                    LogTools.e(TAG, "TOP SCROLL");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    LogTools.e(TAG, "BOTTOM SCROLL");
                    if (mHasNextPage) {
                        loadSpecialAlbum(++mCurrentPage);
                    }
                }
            }
        });
    }

    private void initViews() {
        mViewBanner = mRootView.findViewById(R.id.banner);
        mRvHostList = mRootView.findViewById(R.id.rv_floor_host);
        mRvRecommendList = mRootView.findViewById(R.id.rv_floor_recommend);
        mLayoutHost = mRootView.findViewById(R.id.layout_host);
        mLayoutRecomment = mRootView.findViewById(R.id.layout_album);
        mNsvRoot = mRootView.findViewById(R.id.nsv_main_page_root);
        mViewContainer = mRootView.findViewById(R.id.view_main_page_root);
        mTvRecommendTitle = mRootView.findViewById(R.id.tv_host_label);

        initSpecialAlbumView();
        initCelebrityView();
    }

    /**
     * 主讲嘉宾
     */
    private void initCelebrityView() {
        mTvRecommendTitle.setVisibility(View.INVISIBLE);
        mRvHostList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        float padding = mContext.getResources().getDimension(R.dimen.main_page_recommend_item_margin);
        mRvHostList.addItemDecoration(new TabItemDecoration(Math.round(padding)));

        mAdapterCelebrity = new CelebrityIconItemAdapter(mContext, R.layout.celebrity_icon_item);
        mAdapterCelebrity.setDatas(mListCelebrity);
        mAdapterCelebrity.setOnMyItemClickListener(new CelebrityIconItemAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
                handleCelebritySelect(position);
            }
        });

        mRvHostList.setAdapter(mAdapterCelebrity);
    }

    /**
     * 跳转到搜索结果也
     *
     * @param position
     */
    private void handleCelebritySelect(int position) {
//        if (mListCelebrity != null && mListCelebrity.size() > 0
//                && position >= 0 && position < mListCelebrity.size()) {
//            MainPageData.HomeRecommendEntity celebrity = mListCelebrity.get(position);
//
//            if (celebrity != null) {
//                // 首页进入的搜索结果默认切刀第二个tab——主讲人，不保存搜索历史
//                SearchHelper searchHelper = new SearchHelper();
//                searchHelper.doSearch(celebrity.getResourceName(), 1, false);
//                Router.getInstance().open(SearchResultActivity.class, getActivity());
//            }
//        }

        MainPageData.HomeRecommendEntity entity = mListCelebrity.get(position);
        if (entity != null) {
            EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType,
                    PageUtils.getPageType(entity.getResourceType(), false), entity.getResourceCode()));
        }
    }

    /**
     * 专题列表
     */
    private void initSpecialAlbumView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRvRecommendList.setLayoutManager(layoutManager);
        mRvRecommendList.setNestedScrollingEnabled(false);

        mRvRecommendList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRvRecommendList.setLoadingMoreProgressStyle(ProgressStyle.CubeTransition);
        mRvRecommendList.setArrowImageView(R.drawable.iconfont_downgrey);

        mFooterLoadingMore = new LoadingFooter(mContext);
        mRvRecommendList.addFooterView(mFooterLoadingMore);

        // 初始时都不显示footer
        mFooterLoadingMore.setState(LoadingFooter.State.Normal, false);

        mAdapterAlbum = new VideoItemAdapter(mContext, R.layout.album_grid_2_item);
        mAdapterAlbum.setDatas(mListAlbum);
        mAdapterAlbum.setOnMyItemClickListener(new VideoItemAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mAdapterAlbum != null && mAdapterAlbum.getDatas() != null
                        && mAdapterAlbum.getDatas().size() > 0) {
                    handleGoToPlayer(mAdapterAlbum.getDatas().get(position));
                }
            }
        });

        mRvRecommendList.setAdapter(mAdapterAlbum);
    }

    /**
     * 跳转到播放页面
     *
     * @param specialAlbums
     */
    private void handleGoToPlayer(SpecialAlbums specialAlbums) {
        if (specialAlbums != null) {
            Album album = specialAlbums.getAlbum();

            if (album != null) {
                PlayerHelper.gotoPlayer(getActivity(), PlayerHelper.makePlayerData(album.getId(),
                        "", ConstData.VIDEO_DEFINITION_TYPE_STAND));
            }
        }
    }

    private void showLoadingMoreFooter(boolean show) {
        if (show) {
            mFooterLoadingMore.setState(LoadingFooter.State.Loading);
        } else {
            mFooterLoadingMore.setState(LoadingFooter.State.TheEnd);
        }
    }

    private void initBanner(List<MainPageData.HomeNewsEntity> newsEntities) {
        if (newsEntities != null) {
            List<String> images = new ArrayList<>(newsEntities.size());
            for (MainPageData.HomeNewsEntity entity : newsEntities) {
                if (entity != null && entity.getNewsBanner() != null) {
                    images.add(entity.getNewsBanner());
                }
            }

            mViewBanner.setImages(images).setImageLoader(new BannerImageLoader()).start();
        }
    }

    private void calculateBannerHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewBanner.getLayoutParams();
        params.height = UiTools.dip2px(200);//StatisticsReportUtil.getScreenWidth() * 182 / 375;
        mViewBanner.setLayoutParams(params);
    }

    private static class BannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (path != null && path instanceof String) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                com.jbsx.utils.image.ImageLoader.displayImage((String)path, imageView);
            }
        }
    }

    /**
     * Banner 楼层进度条
     * @param add
     */
    private void toggleMainPageProgress(boolean add) {
        if (add) {
            ProgressBarHelper.addProgressBar(mViewBanner);
        } else {
            ProgressBarHelper.removeProgressBar(mViewBanner);
        }
    }

    /**
     * 学术艺术顾问楼层
     *
     * @param add
     */
    private void toggleCelebrityProgress(boolean add) {
        if (add) {
            ProgressBarHelper.addProgressBar(mRvHostList);
        } else {
            ProgressBarHelper.removeProgressBar(mRvHostList);
        }
    }

    /**
     * 专题系列楼层
     *
     * @param add
     */
    private void toggleSpecialAlbumProgress(boolean add) {
        if (add) {
            ProgressBarHelper.addProgressBar(mRvRecommendList);
        } else {
            ProgressBarHelper.removeProgressBar(mRvRecommendList);
        }
    }

    private void loadMainPageInfo() {
        toggleMainPageProgress(true);
        mPresenter.requestMainPageInfo();
    }

    private void loadSpecialAlbum(int page) {
//        toggleSpecialAlbumProgress(true);
//        mPresenter.requestSpecialAlbumList(page);
    }

    private void loadCelebrities() {
//        toggleCelebrityProgress(true);
//        mPresenter.requestCelebrities();
    }

    @Override
    public void drawMainPageInfo(MainPageData data) {
        if (data != null && data.getBody() != null) {
            mData = data;
            initBanner(data.getBody().getNewsList());
            drawCelebritiesInfo(data.getBody().getRecommends());
            mTvRecommendTitle.setVisibility(View.VISIBLE);
        }

        toggleMainPageProgress(false);
    }

    @Override
    public void drawEmptyData(String errorMessage) {
        toggleMainPageProgress(false);
        ReloadBarHelper.addReloadBar(mViewBanner, errorMessage, new Runnable() {
            @Override
            public void run() {
                loadMainPageInfo();
            }
        });
    }

    @Override
    public void drawHostInfo(HostData hostData) {

    }

    @Override
    public void drawSpecialAlbumInfo(SpecialAlbumData albumsData) {
        if (albumsData != null && albumsData.getPayload() != null) {
            List<SpecialAlbums> data = albumsData.getPayload().getSpecialAlbums();
            if (data != null && !data.isEmpty()) {
                mAdapterAlbum.addList(data);
            }

            // 是否还有下一页
            mHasNextPage = hasNextPage(data);
        }

        handleAlbumLoadMoreComplete();
        toggleSpecialAlbumProgress(false);
    }

    @Override
    public void drawEmptySpecialAlbum(String errorMessage) {
        mHasNextPage = false;
        toggleSpecialAlbumProgress(false);
        ReloadBarHelper.addReloadBar(mRvRecommendList, errorMessage, new Runnable() {
            @Override
            public void run() {
                loadSpecialAlbum(mCurrentPage);
            }
        });
    }

    private void handleAlbumLoadMoreComplete() {
        mRvRecommendList.loadMoreComplete();
        mRvRecommendList.setLoadingMoreEnabled(mHasNextPage);
        showLoadingMoreFooter(mHasNextPage);
    }

    private boolean listNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    private boolean hasNextPage(List data) {
        return !(data == null || data.size() < ConstData.DEFAULT_PAGE_SIZE);
    }

    /**
     * 渲染名家列表
     *
     * @param celebrityData
     */
    public void drawCelebritiesInfo(List<MainPageData.HomeRecommendEntity> celebrityData) {
        if (celebrityData != null && !celebrityData.isEmpty()) {
            mAdapterCelebrity.clear();
            mAdapterCelebrity.addList(celebrityData);
            mListCelebrity = mAdapterCelebrity.getDatas();
        }

        toggleCelebrityProgress(false);
    }

    @Override
    public void drawEmptyCelebrities(String errorMessage) {
        toggleCelebrityProgress(false);
        ReloadBarHelper.addReloadBar(mRvHostList, errorMessage, new Runnable() {
            @Override
            public void run() {
                loadCelebrities();
            }
        });
    }

    /**
     * 联网失败
     */
    @Override
    public void drawNetError() {
        ErroBarHelper.addErroBar(mViewContainer, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
            @Override
            public void run() {
                ErroBarHelper.removeErroBar(mViewContainer);
                loadData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
