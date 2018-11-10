package com.translatmaster.view.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.interactor.MainViewUserCase;
import com.translatmaster.R;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.view.main.contact.MainPageContact;
import com.translatmaster.view.main.entity.BannerData;
import com.translatmaster.view.main.entity.HostData;
import com.translatmaster.view.main.presenter.MainPagePresenter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

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
    private RecyclerView mRvRecommendList;

    private MainPageContact.Presenter mPresenter;

    private BannerData mBannerData;

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
    }

    private void initViews() {
        mViewBanner = mRootView.findViewById(R.id.banner);
        mRvHostList = mRootView.findViewById(R.id.rv_floor_host);
        mRvRecommendList = mRootView.findViewById(R.id.rv_floor_recommend);
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
            if (path != null && path instanceof BannerData.SpecialAlbums) {
                BannerData.SpecialAlbums albums = (BannerData.SpecialAlbums)path;
                if (albums != null && albums.getAlbum() != null) {
                    com.translatmaster.utils.image.ImageLoader.displayImage(albums.getAlbum().getAppImageUrl(), imageView);
                }
            }
        }
    }

    private void loadBannerInfo() {
        mPresenter.requestBannerInfo();
    }

    @Override
    public void drawBannerInfo(BannerData bannerData) {
        if (bannerData != null && bannerData.getPayload() != null) {
            mBannerData = bannerData;
            initBanner(bannerData.getPayload().getSpecialAlbums());
        }
    }

    @Override
    public void drawHostInfo(HostData hostData) {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadBannerInfo();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Subscribe Event bus
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(BaseEvent event) {/* Do something */};

    /**
     * Post events: No need to register and unregister in every child (Activity, Fragment)
     */
//    EventBus.getDefault().post(new MessageEvent());
}
