package com.jbsx.view.login.customview;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.view.login.data.LoginConstData;
import com.jbsx.view.web.WebHelper;

/**
 * 用户协议控件
 */

public class LoginUserAgreementView extends LinearLayout {
    private Context mContext;

    private View mRootView;
    private CheckBox mChkBox;
    private TextView mTxtView;

    public LoginUserAgreementView(Context context) {
        super(context);
        onViewCreate(context);
    }

    public LoginUserAgreementView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onViewCreate(context);
    }

    private void onViewCreate(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mRootView = inflater.inflate(R.layout.login_user_agreement_view, null);
        this.addView(mRootView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        findViews();
        initViews();
        registEvents();
    }

    private void findViews() {
        mChkBox = (CheckBox) findViewById(R.id.chxAgree);
        mTxtView = (TextView) findViewById(R.id.txtUserAgreement);
    }

    private void initViews() {
        mTxtView.setText(Html.fromHtml("已阅读并同意 <u><font color='#333333'>用户协议</font></u>（含隐私政策）"));
    }

    private void registEvents() {
        mChkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mTxtView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoViewUserAgreement();
            }
        });
    }

    public boolean getUserChecked() {
        return mChkBox.isChecked();
    }

    public void checkAgreement(boolean check) {
        mChkBox.setChecked(check);
    }

    private void gotoViewUserAgreement() {
        WebHelper.openWeb(mContext, LoginConstData.LINK_USER_AGREEMENT, "用户服务协议");
    }

    public void setOnCheckStatusChanged(final OnClickListener callback) {
        mChkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(v);
                }
			}
		});
    }
}
