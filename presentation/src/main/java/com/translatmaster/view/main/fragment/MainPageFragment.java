package com.translatmaster.view.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MainPageUserCase;
import com.translatmaster.R;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.utils.LogTools;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.view.main.adapter.VideoItemAdapter;
import com.translatmaster.view.main.contact.MainPageContact;
import com.translatmaster.view.main.entity.BannerData;
import com.translatmaster.view.main.entity.HostData;
import com.translatmaster.view.main.entity.SpecialAlbumData;
import com.translatmaster.view.main.entity.SpecialAlbums;
import com.translatmaster.view.main.presenter.MainPagePresenter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.refresh.LoadingMoreFooter;
import com.zhouyou.recyclerview.refresh.ProgressStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Li Jian
 */

public class MainPageFragment extends BaseFragment implements MainPageContact.View {
    private final static String TAG = "MainPageFragment";

    private View mRootView;
    private Banner mViewBanner;
    private RecyclerView mRvHostList;

    private ViewGroup mLayoutHost;
    private ViewGroup mLayoutRecomment;
    private XRecyclerView mRvRecommendList;
    private VideoItemAdapter mAdapterAlbum;
    private List<SpecialAlbums> mListAlbum = new ArrayList<>();
    private LoadingMoreFooter mFooterLoadingMore;
    private NestedScrollView mNsvRoot;

    private MainPageContact.Presenter mPresenter;

    private BannerData mBannerData;

    /** 当前页码 */
    private int mCurrentPage = 1;
    private boolean mHasNextPage = true;

    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    public void createPresenter() {
        MainPageUserCase userCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new MainPagePresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.main_page_fragment, null, false);
        ButterKnife.bind(this, mRootView);
        createPresenter();
        initViews();
        initEvents();

        return mRootView;
    }

    private void initEvents() {
        mViewBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ShowTools.toast(position + "");
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

        initSpecialAlbumView();
    }

    private void initSpecialAlbumView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRvRecommendList.setLayoutManager(layoutManager);
        mRvRecommendList.setNestedScrollingEnabled(false);

        mRvRecommendList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRvRecommendList.setLoadingMoreProgressStyle(ProgressStyle.CubeTransition);
        mRvRecommendList.setArrowImageView(R.drawable.iconfont_downgrey);

        mFooterLoadingMore = new LoadingMoreFooter(mContext);
        mFooterLoadingMore.setProgressStyle(ProgressStyle.CubeTransition);
        mFooterLoadingMore.setLoadingHint("加载中");

        mRvRecommendList.addFooterView(mFooterLoadingMore);

        // 初始时都不显示footer
        showLoadingMoreFooter(false);

        mAdapterAlbum = new VideoItemAdapter(mContext, R.layout.album_grid_2_item);
        mAdapterAlbum.setDatas(mListAlbum);

        mRvRecommendList.setAdapter(mAdapterAlbum);

    }

    private void showLoadingMoreFooter(boolean show) {
        if (show) {
            mFooterLoadingMore.setState(LoadingMoreFooter.STATE_LOADING);
        } else {
            mFooterLoadingMore.setState(LoadingMoreFooter.STATE_NOMORE);
            mFooterLoadingMore.setNoMoreHint("已经到底了");
        }
    }

    private void initBanner(List images) {
        if (images != null && !images.isEmpty()) {
            mViewBanner.setImages(images)
                    .setImageLoader(new BannerImageLoader())
                    .start();
        }
    }

    private static class BannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (path != null && path instanceof SpecialAlbums) {
                SpecialAlbums albums = (SpecialAlbums)path;
                if (albums != null && albums.getAlbum() != null) {
                    com.translatmaster.utils.image.ImageLoader.displayImage(albums.getAlbum().getAppImageUrl(), imageView);
                }
            }
        }
    }

    private void loadBannerInfo() {
        mPresenter.requestBannerInfo();
    }

    private void loadSpecialAlbum(int page) {
        mPresenter.requestSpecialAlbumList(page);
    }

    @Override
    public void drawBannerInfo(BannerData bannerData) {
        if (bannerData != null && bannerData.getPayload() != null) {
            mBannerData = bannerData;
            initBanner(bannerData.getPayload().getSpecialAlbums());
        }
    }

    @Override
    public void drawEmptyBanner() {
        ShowTools.toast("我擦联网失败");
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

        // 模块是否可见
        handleFloorVisible(mLayoutRecomment, mAdapterAlbum.getDatas());
        handleAlbumLoadMoreComplete();
    }

    private void handleAlbumLoadMoreComplete() {
        mRvRecommendList.loadMoreComplete();
        mRvRecommendList.setLoadingMoreEnabled(mHasNextPage);
        showLoadingMoreFooter(mHasNextPage);
    }

    /**
     * 楼层是否可见
     *
     * @param floorLayout
     * @param data
     */
    private void handleFloorVisible(View floorLayout, List data) {
        if (floorLayout != null) {
            boolean hasList = listNotEmpty(data);
            floorLayout.setVisibility(hasList ? View.VISIBLE : View.GONE);
        }
    }

    private boolean listNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    private boolean hasNextPage(List data) {
        return !(data == null || data.size() < ConstData.DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBannerInfo();
        loadSpecialAlbum(mCurrentPage);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
