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
import com.jbsx.customview.recyclerview.LoadingFooter;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.view.main.adapter.VideoItemAdapter;
import com.jbsx.view.main.entity.SpecialAlbumData;
import com.jbsx.view.main.entity.SpecialAlbums;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.refresh.ProgressStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 宫格列表样式
 */
public class FeedFragment extends BaseFragment {
    private View mRootView;
    private XRecyclerView mRvRecommendList;
    private LoadingFooter mFooterLoadingMore;

    private VideoItemAdapter mAdapterAlbum;
    private List<SpecialAlbums> mListAlbum = new ArrayList<>();

    /** 当前页码 */
    private int mCurrentPage = 1;
    private boolean mHasNextPage = true;

    private MainPageUserCase mUserCase;

    public FeedFragment() {
        // Required empty public constructor
        mUserCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    public void createRequester() {
        mUserCase = new MainPageUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.feed_fragment, null, false);
        createRequester();
        initViews();
        initEvents();
        initSpecialAlbumView();
        loadData();
        return mRootView;
    }

    private void initViews() {
        mRvRecommendList = mRootView.findViewById(R.id.rv_floor_recommend);
    }

    private void loadData() {
        MainApplicationLike.getInstance().getHanlder().post(new Runnable() {
            @Override
            public void run() {
                loadSpecialAlbum(mCurrentPage);
            }
        });
    }

    private void initEvents() {
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
    }

    private void loadSpecialAlbum(int page) {
        toggleSpecialAlbumProgress(true);
        requestSpecialAlbumList(page);
    }

    public void requestSpecialAlbumList(int page) {
        if (mUserCase != null) {
            mUserCase.requestSpecialAlbusList(page, new BaseRequestCallback() {
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
                    drawNetError();
                }
            });
        }
    }

    private void handleLoadAlbumSuccessful(String data) {
        SpecialAlbumData parseData = ParseUtil.parseData(data, SpecialAlbumData.class);
        drawSpecialAlbumInfo(parseData);
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
        toggleSpecialAlbumProgress(false);
    }

    private boolean hasNextPage(List data) {
        return !(data == null || data.size() < ConstData.DEFAULT_PAGE_SIZE);
    }

    private void toggleSpecialAlbumProgress(boolean add) {
        if (add) {
            ProgressBarHelper.addProgressBar(mRvRecommendList);
        } else {
            ProgressBarHelper.removeProgressBar(mRvRecommendList);
        }
    }

    private void handleAlbumLoadMoreComplete() {
        mRvRecommendList.loadMoreComplete();
        mRvRecommendList.setLoadingMoreEnabled(mHasNextPage);
        showLoadingMoreFooter(mHasNextPage);
    }

    private void showLoadingMoreFooter(boolean show) {
        if (show) {
            mFooterLoadingMore.setState(LoadingFooter.State.Loading);
        } else {
            mFooterLoadingMore.setState(LoadingFooter.State.TheEnd);
        }
    }

    private void handleLoadAlbumFailed(BaseDomainData data) {
        drawEmptySpecialAlbum(MessageTools.getMessage(data));
    }

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
//                    handleGoToPlayer(mAdapterAlbum.getDatas().get(position));
                }
            }
        });

        mRvRecommendList.setAdapter(mAdapterAlbum);
    }
}
