package com.jbsx.view.main.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.UiTools;

public class SearchWindow {
    private View mRootView;
    private TextView mBtnLabel;
    private View mBtnSearch;
    private EditText mEditSearchInput;
    private RadioGroup mRadioGroup;

    private PopupWindow mPopWindow;
    private View mParentView;

    public SearchWindow() {
        initPopWindow();
        findViews();
        initEvent();
    }

    private void initPopWindow() {
        mRootView =  LayoutInflater.from(MainApplicationLike.getAppContext()).inflate(R.layout.search_window_view, null);
        mPopWindow = new PopupWindow(mRootView);
        mPopWindow.setWidth(UiTools.dip2px(500));
        mPopWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    private void findViews() {
        mBtnLabel = mRootView.findViewById(R.id.btn_common_label);
        mBtnLabel.setText("检索");
        mBtnSearch = mRootView.findViewById(R.id.btn_search);
        mEditSearchInput = mRootView.findViewById(R.id.tv_search_input);
        mRadioGroup = mRootView.findViewById(R.id.rv_search_radio_group);
    }

    private void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                ShowTools.toast(button.getText().toString());
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearch();
            }
        });

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    public void showSearchDialog(View parentView) {
        mParentView = parentView;
        if (mParentView != null) {
            mPopWindow.showAsDropDown(mParentView, 0, 0);
        }
    }

    public void closeSearchDialog() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

    private void handleSearch() {
        ShowTools.toast("search");
    }
}
