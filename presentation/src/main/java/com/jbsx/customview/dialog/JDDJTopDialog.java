package com.jbsx.customview.dialog;

import android.content.Context;

import com.jbsx.R;

/**
 * 操作按钮在顶部的对话框
 */
public class JDDJTopDialog extends JDDJDialog {

    private static JDDJTopDialog instance;

    public static JDDJTopDialog newInstance() {
        if (instance == null) {
            synchronized (JDDJTopDialog.class) {
                if (instance == null)
                    instance = new JDDJTopDialog();
            }
        }
        return instance;

    }

    public JDDJTopDialog init(Context mContext) {
        initView(mContext, R.layout.dialog_universal_top);
        return instance;
    }

    @Override
    protected int getContentViewId() {
        return R.id.ll_dialog_universal_content;
    }

}
