package com.jbsx.view.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MainPageUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.RoundCornerImageView;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.image.IImageLoadListener;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.data.BackKeyEvent;
import com.jbsx.view.data.CloseVideoEvent;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.adapter.LocalResourceAdapter;
import com.jbsx.view.main.entity.NavigationData;
import com.jbsx.view.main.util.PageUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看大图
 */
public class BigImagePreviewFragment extends BaseFragment {

    private View mRootView;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    private RoundCornerImageView mIvBigImage;
    private ImageView mIvClosePreview;
    private ViewGroup mViewBigPreview;

    public BigImagePreviewFragment() {
        // Required empty public constructor
    }

    public static BigImagePreviewFragment newInstance(String naviId, String naviType, Integer pageType,
                                                      String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        BigImagePreviewFragment contentFragment = new BigImagePreviewFragment();
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
        mRootView = inflater.inflate(R.layout.big_image_preview_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();
        loadData();

        return mRootView;
    }

    public void createPresenter() {
    }

    private void loadData() {
        showBigImage(mRequestParams);
    }

    private void initViews() {
        mIvBigImage = mRootView.findViewById(R.id.iv_gallery_big_image);
        int radius = UiTools.dip2px(8);
        mIvBigImage.setCornerRadii(radius, radius, radius, radius);
        mIvClosePreview = mRootView.findViewById(R.id.iv_gallery_big_image_close);
        mViewBigPreview = mRootView.findViewById(R.id.layout_gallery_big_image);
    }

    private void initEvents() {
        mIvClosePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new BackKeyEvent());
            }
        });
    }

    private void showBigImage(String imagePath) {
        ProgressBarHelper.addProgressBar(mIvBigImage);
        ImageLoader.loadImage(imagePath, new IImageLoadListener() {
            @Override
            public void onLoadingFailed(Drawable errorDrawable) {
                ProgressBarHelper.removeProgressBar(mIvBigImage);
            }

            @Override
            public void onLoadingComplete(Drawable drawable) {
                ProgressBarHelper.removeProgressBar(mIvBigImage);
                mIvBigImage.setImageDrawable(drawable);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
