package com.jbsx.customview.dialog;

import android.content.Context;

import com.jbsx.R;

/**
 * 操作按钮在底部的对话框
 */
public class JDDJBottomDialog extends JDDJDialog {

    public static JDDJBottomDialog newInstance() {

        if (instance == null) {
            synchronized (JDDJBottomDialog.class) {
                if (instance == null)
                    instance = new JDDJBottomDialog();
            }
        }
        return instance;

    }

    public JDDJBottomDialog init(Context mContext) {
        initView(mContext, R.layout.dialog_universal_bottom);
        return instance;
    }

    @Override
    protected int getContentViewId() {
        return R.id.ll_dialog_universal_content;
    }
}
