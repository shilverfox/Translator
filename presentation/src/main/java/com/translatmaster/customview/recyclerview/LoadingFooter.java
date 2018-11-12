package com.translatmaster.customview.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.translatmaster.R;


/**
 * ListView/GridView/RecyclerView 分页加载时使用到的FooterView
 */
public class LoadingFooter extends RelativeLayout {

    protected State mState = State.Normal;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private TextView mEndText;
    private String strEndText = "已经到底啦";


    public LoadingFooter(Context context) {
        super(context);
        init(context, false);
    }

    public LoadingFooter(Context context, boolean isPaddingView) {
        super(context);
        init(context, isPaddingView);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, false);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, false);
    }

    public void init(Context context, boolean isPaddingView) {
        inflate(context, R.layout.sample_common_list_footer, this);
        setOnClickListener(null);
        setState(State.Normal, true);
    }

    public State getState() {
        return mState;
    }

    public void setState(State status) {
        setState(status, true);
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(State status, boolean showView) {
//        DLog.d("hxt","setState--0--status--"+status);
        if (mState == status) {
            return;
        }
//        DLog.d("hxt","setState--1--status--"+status);
        mState = status;
        changeViewState(status, showView);

    }

    public void changeViewState(State status, boolean showView) {
        switch (status) {

            case Normal:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                break;
            case Loading:
                setOnClickListener(null);
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
                    if (viewStub != null) {
                        mLoadingView = viewStub.inflate();
                    }

                } else {
                    mLoadingView.setVisibility(showView ? VISIBLE : GONE);
                }


                break;
            case TheEnd:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mTheEndView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
                    if (viewStub != null) {
                        mTheEndView = viewStub.inflate();
                        mEndText = (TextView) mTheEndView.findViewById(R.id.loading_text);
                        mEndText.setText(strEndText);
                    }

                } else {
                    mTheEndView.setVisibility(showView ? VISIBLE : GONE);
                }

                break;
            case NetWorkError:

                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.network_error_viewstub);
                    if (viewStub != null) {
                        mNetworkErrorView = viewStub.inflate();
                    }
                } else {
                    mNetworkErrorView.setVisibility(showView ? VISIBLE : GONE);
                }

                break;

            case ONE_PAGE:
                setOnClickListener(null);

                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }
                break;
            default:

                break;
        }
    }

    public void setStrEndText(String strEndText) {
        this.strEndText = strEndText;
    }

    public static enum State {
        Normal/**正常*/
        , TheEnd/**加载到最底了*/
        , Loading/**加载中..*/
        , NetWorkError/**网络异常*/
        , ONE_PAGE
    }
}