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

    private String mClassifyCode;
    private MainPageUserCase mUserCase;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String classifyCode) {
        Bundle bundle = new Bundle();
        bundle.putString("classifyCode", classifyCode);

        GalleryFragment contentFragment = new GalleryFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mClassifyCode = bundle.getString("classifyCode");
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
        mUserCase.requestGalleryInfo(mClassifyCode, new BaseRequestCallback() {
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
