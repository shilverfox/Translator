package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;

/**
 * 视频播放页
 */
public class VideoPlayerFragment extends BaseFragment {
    private View mRootView;
    private WebView webView;

    public VideoPlayerFragment() {
        // Required empty public constructor
    }

    public static VideoPlayerFragment newInstance() {
        return new VideoPlayerFragment();
    }

    public void createPresenter() {
        MyInfoUserCase userCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.video_player_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();
        playVideo("https://haokan.baidu.com/?sfrom=baidu-top");//https://daojia.jd.com/activity/new/activityHtml/douguoFoods/detail.html?tabType=recommend&resourceId=21");

        return mRootView;
    }

    private void initViews() {
        webView = mRootView.findViewById(R.id.web_video_player);
    }

    private void initEvents() {

    }

    private void playVideo(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
