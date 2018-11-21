package com.jbsx.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jbsx.R;
import com.jbsx.utils.UiTools;

public class PushFromBottomDialog extends Dialog {

    public PushFromBottomDialog(Context context) {
        super(context);
    }

    public PushFromBottomDialog(Context context, int layoutId) {
        super(context, R.style.custom_dialog_white);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout contentView = (LinearLayout) inflater.inflate(layoutId,
                null);
        init(context, contentView);
    }

    public PushFromBottomDialog(Context context, View contentView) {
        super(context, R.style.custom_dialog_white);
        init(context, contentView);
    }

    private void init(Context context, View contentView) {
        int outsideMenuWidth = 0;

        WindowManager.LayoutParams localLayoutParams = getWindow()
                .getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.x = outsideMenuWidth;
        localLayoutParams.y = 0;

        int screenWidth = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth();
        contentView.setMinimumWidth(screenWidth - outsideMenuWidth);

        onWindowAttributesChanged(localLayoutParams);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(contentView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置大小
     *
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = height;
        params.width = width;
        getWindow().setAttributes(params);
    }
}
