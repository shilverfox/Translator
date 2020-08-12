package com.jbsx.view.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.bumptech.glide.Glide;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.gallery.CoverFlowLayoutManger;
import com.jbsx.customview.gallery.RecyclerCoverFlow;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.entity.GalleryData;
import com.jbsx.view.main.entity.NavigationData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地资源
 */
public class LocalResourceFragment extends BaseFragment {
    private View mRootView;
    private ViewGroup mContainerView;
    private RecyclerCoverFlow mList;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    private MainPageUserCase mUserCase;
    private List<NavigationData.ClassifyEntity> mGalleryData = new ArrayList<>();;
    private Adapter mAdapter;

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
        mGalleryData.add(createLocalData("", "本地图集", "0"));
        mGalleryData.add(createLocalData("", "本地资源", "1"));
        mGalleryData.add(createLocalData("", "新闻资讯", "2"));
        mAdapter.notifyDataSetChanged();
    }

    private void handleLoadFailed(BaseDomainData data) {
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
        ErroBarHelper.addErroBar(mContainerView, ErroBarHelper.ERRO_TYPE_NET_INTERNET, new Runnable() {
            @Override
            public void run() {
                ErroBarHelper.removeErroBar(mContainerView);
                loadData();
            }
        });
    }

    private void initViews() {
        mContainerView = mRootView.findViewById(R.id.view_local_root);
        mList = mRootView.findViewById(R.id.tv_local_info);
//        mList.setFlatFlow(true); //平面滚动
        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
//        mList.setLoop(); //循环滚动，注：循环滚动模式暂不支持平滑滚动
        mAdapter = new Adapter(mContext);
        mList.setAdapter(mAdapter);
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                ((TextView)mRootView.findViewById(R.id.tv_local_indicator)).setText((position+1)+"/"+mList.getLayoutManager().getItemCount());
            }
        });
    }

    private void initEvents() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private Context mContext;
        public Adapter(Context c) {
            mContext = c;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_layout_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (mGalleryData == null || mGalleryData.isEmpty()) {
                return;
            }

            final NavigationData.ClassifyEntity entity = mGalleryData.get(position);
            holder.mTvLabel.setText(entity.getClassifyName());
            Glide.with(mContext).load(entity.getClassifyPreview()).into(holder.img);
//        Glide.with(mContext).load(mColors[position]).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                holder.img.setImageBitmap(BitmapUtil.createReflectedBitmap(resource));
//            }
//        });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType, mPageType, entity.getClassifyCode()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mGalleryData == null ? 0 : mGalleryData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView mTvLabel;
            public ViewHolder(View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img);
                mTvLabel = itemView.findViewById(R.id.tv_gallery_label);
            }
        }
    }
}
