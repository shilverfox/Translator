package com.translatmaster.view.login.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.utils.MessageTools;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.view.login.data.AccountVerifyEvent;
import com.translatmaster.view.login.data.LoginConstData;
import com.translatmaster.view.login.data.LoginData;
import com.translatmaster.view.login.loginsdk.model.AppFailResult;
import com.translatmaster.view.login.loginsdk.model.AppPicDataInfo;
import com.translatmaster.view.login.view.fragment.html.LoginHtmlHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * 登录工具类
 * <p>
 * Created by lijian15 on 2016/8/17.
 */

public class LoginUtils {

    /**
     * 显示错误提示信息
     *
     * @param error
     */
    public static void showErrorMessage(String error) {
        String msg = TextUtils.isEmpty(error) ? MessageTools.DEFAULT_ERROR_MESSAGE : error;
        ShowTools.toast(msg);
    }

    /**
     * 进入主界面
     *
     * @param mobile
     */
    private static void gotoMainActivity(EventBus eventBus, Context context, String mobile, String djPin) {
        if (context == null || eventBus == null) {
            return;
        }

        // 关闭当前Activity
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    /**
     * 绑定成功后跳转判断
     *
     * @param eventBus
     * @param context
     * @param loginUser
     */
    public static void whereToGo(EventBus eventBus, Context context, LoginData loginUser) {
        if (context == null || eventBus == null || loginUser == null) {
            return;
        }

        LoginHelper.getInstance().setLoginUser(loginUser);
        LoginHelper.getInstance().saveData();
    }

    /**
     * 给View设置图片
     *
     * @param myPicDataInfo
     * @param view
     */
    public static void setVcodeToView(AppPicDataInfo myPicDataInfo, ImageView view) {
        if (view == null || myPicDataInfo == null) {
            return;
        }

        byte[] b = myPicDataInfo.getsPicData();

        if (b != null && b.length > 0) {
            // 返回图片byte[]
            Bitmap bitmap = BitmapFactory.decodeByteArray(myPicDataInfo.getsPicData(), 0,
                    myPicDataInfo.getsPicData().length);
            view.setImageBitmap(bitmap);
        }
    }

    /**
     * 设置按钮样式
     *
     * @param button
     * @param isEnable
     */
    public static void setNormalButtonStatus(Context context, Button button, boolean isEnable) {
        if (button == null || context == null) {
            return;
        }

        button.setEnabled(isEnable);

        if (isEnable) {
            button.setBackgroundResource(R.drawable.longin_button_bg);
            button.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            button.setBackgroundResource(R.drawable.longin_button_bg_disable);
            button.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    public static void setNormalTextStatus(Context context, TextView button, boolean isEnable) {
        if (button == null || context == null) {
            return;
        }

        button.setEnabled(isEnable);

        if (isEnable) {
            button.setBackgroundResource(R.drawable.longin_button_bg);
            button.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            button.setBackgroundResource(R.drawable.longin_button_bg_disable);
            button.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    /**
     * 对账号进行鉴权
     *
     * @param openUrlResult
     * @param requestSrc    请求来源
     */
    public static void handleVerifyAccount(AppFailResult openUrlResult, int requestSrc) {
        if (openUrlResult == null) {
            return;
        }

        LoginHtmlHelper.getInstance().gotoLoginHtmlView(
                LoginHtmlHelper.getInstance().wrapToHtmlData(openUrlResult, requestSrc));

    }

    /**
     * 含有webView的Activity的回退方法
     *
     * @param webActivity
     * @param webView
     */
    public static void backWebView(Activity webActivity, WebView webView) {
        if (webView == null) {
            return;
        }

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (webActivity != null) {
                webActivity.finish();
            }
        }
    }

    /**
     * 隐藏输入法键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputMethod(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void showSoftInputMethod(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.showSoftInput(view, 0);
            }
        }
    }

    public static void toggleSoftInputMethod(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
            }
        }
    }
}
