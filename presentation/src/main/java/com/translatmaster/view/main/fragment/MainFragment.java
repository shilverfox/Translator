package com.translatmaster.view.main.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MainViewUserCase;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.translatmaster.R;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.hotfix.HotFixHelper;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.utils.image.ImageLoader;
import com.translatmaster.view.main.contact.MainContact;
import com.translatmaster.view.main.presenter.MainPresenter;

import java.io.File;

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
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.spn_main_des_language)
    Spinner mSpnMainDesLanguage;

    private View mRootView;

    /** Which language needs to be translated */
    private String mDestLanguage;

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
        MainViewUserCase userCase = new MainViewUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new MainPresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.fragment_main, null, false);
        ButterKnife.bind(this, mRootView);
        createPresenter();
        initViews();

        HotFixHelper.checkHotFix();

        return mRootView;
    }

    /**
     * Draw views in here
     */
    private void initViews() {
        ImageLoader.displayImage("http://t12.baidu.com/it/u=2700338006,3993069872&fm=76", mImageView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.dest_trans_language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnMainDesLanguage.setAdapter(adapter);
        mSpnMainDesLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDestLanguage = mSpnMainDesLanguage.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Update UI in his thread
     */
    @Override
    public void drawTranslatResult(final String content) {
//        Gson gson = new Gson();
//        TranslatData data = null;
//
//        try {
//            data = gson.fromJson(content, TranslatData.class);
//        } catch (Exception e) {
//            mTransResult = e + "";
//            LogTools.e(TAG, mTransResult);
//        }
//
//        if (data != null && data.getData() != null && data.getData().getTranslations() != null
//                && !data.getData().getTranslations().isEmpty()
//                && data.getData().getTranslations().get(0) != null) {
//            mTransResult = data.getData().getTranslations().get(0).getTranslatedText() + "";
//        }

        // Update UI
//        mTextView.setText(content + "");

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                ShowTools.toast(content + "");
//            }
//        });
        ShowTools.toast(content + "");
    }

    @OnClick(R.id.button)
    public void onBtnTranslateClick() {
        String content = mEditText.getText().toString();

        if (!TextUtils.isEmpty(content)) {
            if (mPresenter != null) {
                mPresenter.requestTranslate(content, "", mDestLanguage);
            }
        } else {
            ShowTools.toast(mContext.getResources().getString(R.string.main_alert_content_is_null));
        }
    }

    @OnClick(R.id.button2)
    public void onBtnHotFixClick() {
        String patchPath  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk";
        File file = new File(patchPath);
        if (file.exists()) {
            ShowTools.toast("Found patch file!");
            TinkerInstaller.onReceiveUpgradePatch(MainApplicationLike.getAppContext(), patchPath);
        } else {
            ShowTools.toast("No patch file!!!");
        }
    }

    @OnClick(R.id.button3)
    public void onBtnVerifyClick() {
        ShowTools.toast("I am here!!!!");
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
