package com.jbsx.view.main.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.UiTools;
import com.jbsx.view.data.PageChangeEvent;

import org.greenrobot.eventbus.EventBus;

public class SearchWindow {
    private View mRootView;
    private TextView mBtnLabel;
    private View mBtnSearch;
    private EditText mEditSearchInput;
    private RadioGroup mRadioGroup;

    private PopupWindow mPopWindow;
    private View mParentView;

    private String mNaviType;
    private String mNaviId;

    private int mSearchType = AppConstData.SEARCH_TYPE_ALBUM;

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
                handleTypeCheck(radioGroup, i);
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSearchDialog();
                handleSearch();
            }
        });

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    private void handleTypeCheck(RadioGroup radioGroup, int i) {
        mSearchType = (radioGroup.getCheckedRadioButtonId() == R.id.rb_search_album)
                ? AppConstData.SEARCH_TYPE_ALBUM : AppConstData.SEARCH_TYPE_VIDEO;
    }

    public void showSearchDialog(View parentView, String naviId, String naviType) {
        mParentView = parentView;
        mNaviId = naviId;
        mNaviType = naviType;
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
        String keyWord = null;
        try {
            keyWord = mEditSearchInput.getText().toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(keyWord)) {
            ShowTools.toast("请输入要查询的关键字");
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("search_key", keyWord);
        jsonObject.addProperty("search_type", mSearchType);
        String requestParams = jsonObject.toString();
        EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType,
                AppConstData.PAGE_TYPE_SEARCH_RESULT, requestParams));
    }
}
