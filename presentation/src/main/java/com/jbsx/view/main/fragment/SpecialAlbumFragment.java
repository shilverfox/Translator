package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.jbsx.customview.recyclerview.EndlessRecyclerOnScrollListener;
import com.jbsx.customview.recyclerview.LoadingFooter;
import com.jbsx.player.util.PlayerHelper;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.main.adapter.VideoItemAdapter;
import com.jbsx.view.main.contact.MainPageContact;
import com.jbsx.view.main.entity.Album;
import com.jbsx.view.main.entity.BannerData;
import com.jbsx.view.main.entity.CelebrityData;
import com.jbsx.view.main.entity.HostData;
import com.jbsx.view.main.entity.SpecialAlbumData;
import com.jbsx.view.main.entity.SpecialAlbums;
import com.jbsx.view.main.presenter.MainPagePresenter;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.refresh.IMoreFooter;
import com.zhouyou.recyclerview.refresh.ProgressStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 首页专题
 *
 * Created by Li Jian
 */

public class SpecialAlbumFragment extends BaseFragment {
    private final static String TAG = "SpecialAlbumFragment";

    private View mRootView;
    private XRecyclerView mRvRecommendList;
    private VideoItemAdapter mAdapterAlbum;
    private List<SpecialAlbums> mListAlbum = new ArrayList<>();
    private LoadingFooter mFooterLoadingMore;

    private MainPageUserCase mUserCase;

    /** 当前页码 */
    private int mCurrentPage = 1;
    private boolean mHasNextPage = true;

    public SpecialAlbumFragment() {
        // Required empty public constructor
    }

    public static SpecialAlbumFragment newInstance() {
        return new SpecialAlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.special_album_page_fragment, null, false);
        ButterKnife.bind(this, mRootView);

        initUserCase();
        initViews();
        initEvents();
        loadData();

        return mRootView;
    }

    private void initUserCase() {
        mUserCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    /**
     * 加载网络数据
     */
    private void loadData() {
        ProgressBarHelper.addProgressBar(mRvRecommendList);
        MainApplicationLike.getInstance().getHanlder().post(new Runnable() {
            @Override
            public void run() {
                loadSpecialAlbum(mCurrentPage);
            }
        });
    }

    private void initEvents() {
    }

    private void initViews() {
        mRvRecommendList = mRootView.findViewById(R.id.rv_floor_recommend);
        initSpecialAlbumView();
    }

    /**
     * 专题列表
     */
    private void initSpecialAlbumView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRvRecommendList.setLayoutManager(layoutManager);

        mRvRecommendList.setPullRefreshEnabled(false);
        mRvRecommendList.setLoadingMoreEnabled(false);

        mRvRecommendList.addOnScrollListener(mOnScrollListener);

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

    private void loadSpecialAlbum(int page) {
        mUserCase.requestSpecialAlbum(page, new BaseRequestCallback() {
            @Override
            public void onRequestFailed(BaseDomainData data) {
                handleLoadAlbumFailed(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleLoadAlbumSuccessful(data);
            }

            @Override
            public void onNetError() {
                ErroBarHelper.addErroBar(mRvRecommendList, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                });
            }
        });
    }

    private void handleLoadAlbumSuccessful(String data) {
        SpecialAlbumData parseData = ParseUtil.parseData(data, SpecialAlbumData.class);
        drawSpecialAlbumInfo(parseData);
    }

    private void handleLoadAlbumFailed(BaseDomainData data) {
        ShowTools.toast(MessageTools.getMessage(data));
    }

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
        ProgressBarHelper.removeProgressBar(mRvRecommendList);
    }

    public void drawEmptySpecialAlbum(String errorMessage) {
        mHasNextPage = false;
        ProgressBarHelper.removeProgressBar(mRvRecommendList);
        ReloadBarHelper.addReloadBar(mRvRecommendList, errorMessage, new Runnable() {
            @Override
            public void run() {
                loadSpecialAlbum(mCurrentPage);
            }
        });
    }

    private void handleAlbumLoadMoreComplete() {
        showLoadingMoreFooter(mHasNextPage);
    }

    private boolean listNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    private boolean hasNextPage(List data) {
        return !(data == null || data.size() < ConstData.DEFAULT_PAGE_SIZE);
    }

    /**
     * 联网失败
     */
    public void drawNetError() {
        ErroBarHelper.addErroBar(mRvRecommendList, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
            @Override
            public void run() {
                ErroBarHelper.removeErroBar(mRvRecommendList);
                loadData();
            }
        });
        ProgressBarHelper.removeProgressBar(mRvRecommendList);
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

    /**
     * 分页
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            if (mHasNextPage) {
                loadSpecialAlbum(++mCurrentPage);
            }
        }
    };

}
