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
import com.jbsx.view.main.entity.SpecialAlbumData;

/**
 * 画廊样式
 */
public class GalleryFragment extends BaseFragment {
    private View mRootView;
    private ViewGroup mContainerView;
    private RecyclerCoverFlow mList;

    private String mRequestParams;
    private String mNaviType;
    private String mPageType;
    private String mNaviId;

    private MainPageUserCase mUserCase;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String naviId, String naviType, String pageType,
                                              String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putString(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
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
            mPageType = bundle.getString(AppConstData.INTENT_KEY_PAGE_TYPE);
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
        SpecialAlbumData parseData = ParseUtil.parseData(data, SpecialAlbumData.class);
//        drawSpecialAlbumInfo(parseData);
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
        mContainerView = mRootView.findViewById(R.id.view_gallery_root);
        mList = mRootView.findViewById(R.id.tv_gallery_info);
//        mList.setFlatFlow(true); //平面滚动
        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        mList.setLoop(); //循环滚动，注：循环滚动模式暂不支持平滑滚动
        mList.setAdapter(new Adapter(mContext));
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                ((TextView)mRootView.findViewById(R.id.tv_gallery_indicator)).setText((position+1)+"/"+mList.getLayoutManager().getItemCount());
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

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private Context mContext;
        private int[] mColors = {R.mipmap.item1,R.mipmap.item2,R.mipmap.item3,R.mipmap.item4,
                R.mipmap.item5,R.mipmap.item6};

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
            Glide.with(mContext).load(mColors[position]).into(holder.img);
//        Glide.with(mContext).load(mColors[position]).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                holder.img.setImageBitmap(BitmapUtil.createReflectedBitmap(resource));
//            }
//        });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mColors.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            public ViewHolder(View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img);
            }
        }
    }
}
