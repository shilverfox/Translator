package com.translatmaster.view.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.translatmaster.R;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.utils.LogTools;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.utils.image.ImageLoader;
import com.translatmaster.view.main.contact.MainContact;
import com.translatmaster.view.main.modal.TranslatData;
import com.translatmaster.view.main.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class MainFragment extends BaseFragment implements MainContact.View {
    private final static String TAG = "MainFragment";

    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.imageView)
    ImageView mImageView;

    private TranslatData mData;
    private View mRootView;

    /**  */
    private String mTransResult;

    private MainContact.Presenter mPresenter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void createPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.fragment_main, null, false);
        ButterKnife.bind(this, mRootView);
        createPresenter();
        initViews();

        return mRootView;
    }

    /**
     * Draw views in here
     */
    private void initViews() {
        ImageLoader.displayImage("http://t12.baidu.com/it/u=2700338006,3993069872&fm=76",  mImageView);
    }

    /**
     * Update UI in his thread
     */
    @Override
    public void drawTranslatResult(final String content) {
        Gson gson = new Gson();
        TranslatData data = null;

        try {
            data = gson.fromJson(content, TranslatData.class);
        } catch (Exception e) {
            LogTools.e(TAG, e + "");
        }

        if (data != null && data.getData() != null && data.getData().getTranslations() != null
                && !data.getData().getTranslations().isEmpty()
                && data.getData().getTranslations().get(0) != null) {
            mTransResult = data.getData().getTranslations().get(0).getTranslatedText() + "";
        }

        // Update UI
        if (data != null) {
            MainFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(mTransResult + "");
                }
            });
        }
    }

    @OnClick(R.id.button)
    public void onBtnTranslateClick() {
        String content = mEditText.getText().toString();

        if (!TextUtils.isEmpty(content)) {
            if (mPresenter != null) {
                mPresenter.requestTranslate(content);
            }
        } else {
            ShowTools.toast(mContext.getResources().getString(R.string.main_alert_content_is_null));
        }
    }


    /**
     * Subscribe Event bus
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {/* Do something */};

    /**
     * Post events: No need to register and unregister in every child (Activity, Fragment)
     */
//    EventBus.getDefault().post(new MessageEvent()); 
}
