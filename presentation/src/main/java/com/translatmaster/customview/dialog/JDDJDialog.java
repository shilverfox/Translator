package com.translatmaster.customview.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.utils.UiTools;

/**
 * 默认对话框模板类
 */
public abstract class JDDJDialog {

    protected static JDDJBottomDialog instance;
    protected Context mContext;
    private DialogParams mParams;
    private JDDJListener listener;
    private DialogInterface.OnDismissListener onDismissListener;
    private View mView;
    private Dialog dialog;

    /**
     * 基础布局
     *
     * @param mContext
     * @param layoutId
     */
    protected void initView(Context mContext, int layoutId) {
        this.mView = LayoutInflater.from(mContext).inflate(layoutId, null);
        this.mContext = mContext;
        if (this.mParams == null) {
            this.mParams = new DialogParams();
        } else {
            this.mParams.clear();
        }
        if (this.listener == null) {
            this.listener = new JDDJListener() {
                @Override
                public void dismiss() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            };
        }
    }

    /**
     * 显示对话框
     */
    public final void show() {
        if(mContext!=null && mContext instanceof Activity && !((Activity) mContext).isFinishing()){
            dialog = getDialog();
            setParamsToView(dialog);
            dialog.show();
            dialog.setContentView(mView);

            if(mParams!=null){
                final Window window = dialog.getWindow();
                WindowManager manager=window.getWindowManager();
                final DisplayMetrics dm = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(dm);

                final WindowManager.LayoutParams params = window.getAttributes();
                params.width=(int)(dm.widthPixels*mParams.percentageOfScreenWidth);
                if(mView!=null){
                    mView.post(new Runnable() {
                        @Override
                        public void run() {
                            if(mView!=null){
                                if (mView.getHeight()< (int) (dm.heightPixels * mParams.percentageOfScreenHeight)) {
                                    params.height =  FrameLayout.LayoutParams.WRAP_CONTENT;
                                } else {
                                    params.height = (int) (dm.heightPixels * mParams.percentageOfScreenHeight);
                                }
                            }else{
                                params.height =  FrameLayout.LayoutParams.WRAP_CONTENT;
                            }
                            window.setAttributes(params);
                        }
                    });
                }
            }
        }
    }

    /**
     * 显示对话框
     * 通过测量计算对话框高度，不发送post，防止与动画冲突
     */
    public final void showDialog() {
        if(mContext!=null && mContext instanceof Activity && !((Activity) mContext).isFinishing()){
            dialog = getDialog();
            setParamsToView(dialog);
            dialog.show();
            dialog.setContentView(mView);

            if(mParams!=null){
                final Window window = dialog.getWindow();
                WindowManager manager=window.getWindowManager();
                final DisplayMetrics dm = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(dm);

                final WindowManager.LayoutParams params = window.getAttributes();
                params.width=(int)(dm.widthPixels*mParams.percentageOfScreenWidth);
                if(mView!=null){
                    if(mView!=null){
                        mView.measure(0, 0);
                        if (mView.getMeasuredHeight()< (int) (dm.heightPixels * mParams.percentageOfScreenHeight)) {
                            params.height =  FrameLayout.LayoutParams.WRAP_CONTENT;
                        } else {
                            params.height = (int) (dm.heightPixels * mParams.percentageOfScreenHeight);
                        }
                    }else{
                        params.height =  FrameLayout.LayoutParams.WRAP_CONTENT;
                    }
                    window.setAttributes(params);
                }
            }
        }
    }

    /**
     * 设置用户自定义属性
     */
    private void setParamsToView(Dialog alertDialog) {
        LinearLayout content = (LinearLayout) mView.findViewById(getContentViewId());
        if (mParams.view != null) {
            content.addView(mParams.view);
        }
        TextView tvMsg = (TextView) mView.findViewById(R.id.tv_dialog_universal_msg);
        TextView tvMsg2 = (TextView) mView.findViewById(R.id.tv_dialog_universal_msg2);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_dialog_universal_title);
        if (TextUtils.isEmpty(mParams.msg)) {
            tvMsg.setVisibility(View.GONE);
        } else {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(mParams.msg);
        }
        if (TextUtils.isEmpty(mParams.msg2)) {
            tvMsg2.setVisibility(View.GONE);
        } else {
//            tvMsg2.setVisibility(View.VISIBLE);
            tvMsg2.setMovementMethod(LinkMovementMethod.getInstance());
            tvMsg2.setText(mParams.msg2);
        }
        if (TextUtils.isEmpty(mParams.title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mParams.title);
        }
        if(mParams.firstParams!=null){
            mParams.btnCount++;
        }
        if(mParams.secondParams!=null){
            mParams.btnCount++;
        }
        if(mParams.thirdParams!=null){
            mParams.btnCount++;
        }
        initBtnParams(mView.findViewById(R.id.tv_dialog_universal_first), mParams.firstParams, alertDialog, 0);
        initBtnParams(mView.findViewById(R.id.tv_dialog_universal_second), mParams.secondParams, alertDialog, 1);
        initBtnParams(mView.findViewById(R.id.tv_dialog_universal_three), mParams.thirdParams, alertDialog, 2);
        if (mParams.firstParams == null && mParams.secondParams == null && mParams.thirdParams == null) {
            mView.findViewById(R.id.ll_dialog_universal_operate).setVisibility(View.GONE);
            mView.findViewById(R.id.view_bottom_line).setVisibility(View.GONE);
            ((RelativeLayout.LayoutParams)mView.findViewById(R.id.ll_dialog_universal_content).getLayoutParams()).bottomMargin=0;
        } else {
            mView.findViewById(R.id.ll_dialog_universal_operate).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.view_bottom_line).setVisibility(View.VISIBLE);
        }
        alertDialog.setCancelable(mParams.isCancel);
        if(mParams.animId!=-1){
            alertDialog.getWindow().setWindowAnimations(mParams.animId);
        }
        if (mParams.view == null) {
            if (TextUtils.isEmpty(mParams.title)) {
                if (!TextUtils.isEmpty(mParams.msg)) {
                    tvMsg.setMinimumHeight(UiTools.dip2px(90));
                }
            } else {
                if (TextUtils.isEmpty(mParams.msg)) {
                    tvTitle.setMinimumHeight(UiTools.dip2px(90));
                } else {
                    tvTitle.setMinimumHeight(UiTools.dip2px(45));
                    tvMsg.setMinimumHeight(UiTools.dip2px(45));
                    tvMsg.setPadding(UiTools.dip2px(20), UiTools.dip2px(-15), UiTools.dip2px(20), 0);
                }
            }
        } else {
            if (TextUtils.isEmpty(mParams.title)) {
                if (!TextUtils.isEmpty(mParams.msg)) {
                    tvMsg.setMinimumHeight(UiTools.dip2px(45));
                }
            } else {
                if (TextUtils.isEmpty(mParams.msg)) {
                    tvTitle.setMinimumHeight(UiTools.dip2px(45));
                } else {
                    tvTitle.setMinimumHeight(UiTools.dip2px(45));
                    tvMsg.setMinimumHeight(UiTools.dip2px(45));
                    tvMsg.setPadding(UiTools.dip2px(20), UiTools.dip2px(-15), UiTools.dip2px(20), 0);
                }
            }
        }
    }

    /**
     * 初始按钮参数
     *
     * @param view
     * @param params
     */
    private void initBtnParams(View view, final DialogBtnParams params, final Dialog alertDialog, int type) {
        if (view == null) {
            return;
        }
        TextView tv = (TextView) view;
        if (params == null) {
            tv.setVisibility(View.GONE);
            switch (type) {
                case 0:
                    mView.findViewById(R.id.ll_dialog_universal_first).setVisibility(View.GONE);
                    break;
                case 1:
                    mView.findViewById(R.id.ll_dialog_universal_second).setVisibility(View.GONE);
                    mView.findViewById(R.id.v_dialog_btn1_line).setVisibility(View.GONE);
                    break;
                case 2:
                    mView.findViewById(R.id.v_dialog_btn2_line).setVisibility(View.GONE);
            }
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View dview) {
                    if (params.isClose) {
                        alertDialog.dismiss();
                    }
                    if (params.listener != null) {
                        params.listener.onClick(dview);
                    }
                }
            });
            setBtnTextColor(params,type,tv);
            setBtnBgColor(params,type,tv);
            if (!TextUtils.isEmpty(params.btnTitle)) {
                tv.setText(params.btnTitle);
            }
        }
    }

    private void setBtnTextColor(DialogBtnParams params,int type,TextView tv){
        if(params.hasSetTextColor){
            tv.setTextColor(params.colorForText);
        }else{
            if(mParams.btnCount<=1){
                tv.setTextColor(DialogBtnParams.secondBtnDefaultColor);
            }else {
                if(type==0){
                    tv.setTextColor(DialogBtnParams.firstBtnDefaultColor);
                }else if(type==1){
                    tv.setTextColor(DialogBtnParams.secondBtnDefaultColor);
                }else{
                    tv.setTextColor(DialogBtnParams.firstBtnDefaultColor);
                }
            }
        }
    }


    private void setBtnBgColor(DialogBtnParams params,int type,TextView tv){
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(params.bgColor);
        if(mParams.btnCount<=1){
            gd.setCornerRadii(new float[]{UiTools.dip2px(6), UiTools.dip2px(6), UiTools.dip2px(6), UiTools.dip2px(6), UiTools.dip2px(6), UiTools.dip2px(6), UiTools.dip2px(6), UiTools.dip2px(6)});
        }else {
            if(type==0){
                gd.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, UiTools.dip2px(6), UiTools.dip2px(6)});
            }else if(type==1){
                gd.setCornerRadii(new float[]{0, 0, 0, 0, UiTools.dip2px(6), UiTools.dip2px(6), 0, 0});
            }
        }
        tv.setBackgroundDrawable(gd);
    }

    /**
     * (由子类自行实现)
     *
     * @return
     */
    protected abstract int getContentViewId();

    private Dialog getDialog() {
        Dialog alertDialog = new Dialog(mContext, R.style.holo_dialog);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(dialog);
                }
                mContext = null;//防止泄露
                mView = null;
                mParams.clear();
            }
        });
        return alertDialog;
    }

    /**
     * 点击对话框外不让取消
     *
     * @param isCancel
     * @return
     */
    public final JDDJDialog setCancelable(boolean isCancel) {
        mParams.isCancel = isCancel;
        return this;
    }

    public final JDDJDialog setAnimations(int animId) {
        mParams.animId = animId;
        return this;
    }


    /**
     * 不提供String入参，引导开发者将中文放在String.xml中
     *
     * @param strId
     * @return
     */
    public final JDDJDialog setTitle(int strId) {
        setTitle(mContext.getString(strId));
        return this;
    }

    /**
     * 不提供String入参，引导开发者将中文放在String.xml中
     *
     * @param str
     * @return
     */
    @Deprecated
    public final JDDJDialog setTitle(String str) {
        mParams.title = str;
        return this;
    }

    /**
     * 不提供String入参，引导开发者将中文放在String.xml中
     *
     * @param strId
     * @return
     */
    public final JDDJDialog setMsg(int strId) {
        setMsg(mContext.getString(strId));
        return this;
    }

    /**
     * 之后会删掉，放弃使用
     *
     * @param str
     * @return
     */
    @Deprecated
    public final JDDJDialog setMsg(String str) {
        mParams.msg = str;
        return this;
    }

    public final JDDJDialog setSecondMsg(CharSequence str) {
        mParams.msg2 = str;

        mView.findViewById(R.id.tv_dialog_universal_msg2).setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 所占屏幕高度百分比
     * @param percentageOfScreen
     * @return
     */
    public final JDDJDialog setMaxheight(float percentageOfScreen) {
        mParams.percentageOfScreenHeight=percentageOfScreen;
        return this;
    }

    /**
     * 所占屏幕宽度百分比
     * @param percentageOfScreen
     * @return
     */
    public final JDDJDialog setWidth(float percentageOfScreen) {
        mParams.percentageOfScreenWidth=percentageOfScreen;
        return this;
    }

    /**
     * 设置自定义view
     *
     * @param view
     * @return
     */
    public final JDDJDialog setView(View view) {
        mParams.view = view;
        return this;
    }

    /**
     * 设置左边第一个按钮监听
     *
     * @param listener
     * @param btnTitle
     * @return
     */
    public final JDDJDialog setFirstOnClickListener(String btnTitle, View.OnClickListener listener) {
        mParams.firstParams = new DialogBtnParams(btnTitle, listener, DialogBtnParams.firstBtnDefaultColor, true);
        mParams.firstParams.hasSetTextColor=false;
        return this;
    }

    /**
     * 设置左边第一个按钮监听
     *
     * @param listener
     * @param colorForText
     * @param btnTitle     字体是否需要强调显示
     * @param isClose      是否关闭对话框
     * @return
     */
    public final JDDJDialog setFirstOnClickListener(String btnTitle, View.OnClickListener listener
            , int colorForText, boolean isClose) {
        mParams.firstParams = new DialogBtnParams(btnTitle, listener, colorForText, isClose);
        mParams.firstParams.hasSetTextColor=true;
        return this;
    }

    public final JDDJDialog setFirstOnClickListener(String btnTitle, View.OnClickListener listener
            , int colorForText, int bgColor,boolean isClose) {
        mParams.firstParams = new DialogBtnParams(btnTitle, listener, colorForText, bgColor, isClose);
        mParams.firstParams.hasSetTextColor=true;
        return this;
    }

    /**
     * 设置左边第二个按钮监听
     *
     * @param listener
     * @param btnTitle
     * @return
     */
    public final JDDJDialog setSecondOnClickListener(String btnTitle, View.OnClickListener listener) {
        mParams.secondParams = new DialogBtnParams(btnTitle, listener, DialogBtnParams.secondBtnDefaultColor, true);
        mParams.secondParams.hasSetTextColor=false;
        return this;
    }

    /**
     * 设置左边第二个按钮监听
     *
     * @param listener
     * @param colorForText
     * @param btnTitle     字体是否需要强调显示
     * @param isClose      是否关闭对话框
     * @return
     */
    public final JDDJDialog setSecondOnClickListener(String btnTitle, View.OnClickListener listener
            , int colorForText, boolean isClose) {
        mParams.secondParams = new DialogBtnParams(btnTitle, listener, colorForText, isClose);
        mParams.secondParams.hasSetTextColor=true;
        return this;
    }

    public final JDDJDialog setSecondOnClickListener(String btnTitle, View.OnClickListener listener
            , int colorForText, int bgColor,boolean isClose) {
        mParams.secondParams = new DialogBtnParams(btnTitle, listener, colorForText,bgColor, isClose);
        mParams.secondParams.hasSetTextColor=true;
        return this;
    }

    /**
     * 设置左边第三个按钮监听
     *
     * @param listener
     * @param btnTitle
     * @return
     */
    public final JDDJDialog setThirdOnClickListener(String btnTitle, View.OnClickListener listener) {
        return setThirdOnClickListener(btnTitle, listener, -1, true);
    }

    /**
     * 设置左边第三个按钮监听
     *
     * @param listener
     * @param colorForText
     * @param btnTitle     字体是否需要强调显示
     * @param isClose      是否关闭对话框
     * @return
     */
    public final JDDJDialog setThirdOnClickListener(String btnTitle, View.OnClickListener listener
            , int colorForText, boolean isClose) {
        mParams.thirdParams = new DialogBtnParams(btnTitle, listener, colorForText, isClose);
        return this;
    }

    public void dismiss() {
        if (listener != null) {
            listener.dismiss();
        }
    }

    /**
     * 销毁记得置为null
     * @param listener1
     */
    public void setOnDismissListener (DialogInterface.OnDismissListener listener1) {
        this.onDismissListener = listener1;
    }



    public boolean isShowing(){
            if (dialog == null){
                return false;
            }else {
                return dialog.isShowing();
            }

    }

}

