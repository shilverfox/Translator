package com.jbsx.weixin;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.PushFromBottomDialog;
import com.jbsx.utils.LogTools;
import com.jbsx.utils.ShowTools;
import com.jbsx.weixin.data.CloseShareWindowEvent;
import com.jbsx.weixin.data.IOnShareWindowCloseListener;
import com.jbsx.weixin.data.WechatModel;

public class SharePopWindow {
    public PushFromBottomDialog pop;// 弹框页面
    private LinearLayout shareFriend;
    private LinearLayout shareFriendCircle;
    private SendMessageToWX.Req mReq;
    final private Activity activity;

    /**
     * 分享数据：好友
     */
    private WechatModel mOneFriendModel;

    /**
     * 分享数据：朋友圈
     */
    private WechatModel mFriendsModel;

    private int mShareType;

    private float bgAlpha = -1;

    /**
     * 窗口关闭回调
     */
    private IOnShareWindowCloseListener mOnWindowCloseListener;
    public final static int SHARESOUCE_H5 = 1;
    public final static int SHARESOUCE_PIC = 2;
    public final static int SHARESOUCE_COMMON = 3;

    /**
     * 3.4修改
     *
     * @param activity
     */
    protected SharePopWindow(Activity activity, String path) {
        this(activity, -1, path);
    }

    protected SharePopWindow(Activity activity, float bgAlpha, String path) {
        this.activity = activity;
        this.bgAlpha = bgAlpha;
        init(null, SHARESOUCE_COMMON, null, path, null);
    }

    protected SharePopWindow(Activity activity, View view, int sourcetype, String path) {
        this.activity = activity;
        init(view, sourcetype, null, path, null);
    }

    protected SharePopWindow(Activity activity, View view, int sourcetype, Bitmap bitmap, String path) {
        this(activity, view, sourcetype, bitmap, -1, path);
    }

    protected SharePopWindow(Activity activity, View view, int sourcetype, Bitmap bitmap, float bgAlpha, String path) {
        this.activity = activity;
        this.bgAlpha = bgAlpha;
        init(view, sourcetype, bitmap, path, null);
    }


    protected SharePopWindow(Activity activity, View view, int sourcetype, Bitmap bitmap, float bgAlpha, String path, Bitmap friendcirlce) {
        this.activity = activity;
        this.bgAlpha = bgAlpha;
        init(view, sourcetype, bitmap, path, friendcirlce);
    }

    /**
     * 注册窗口回调
     * 3.4增加
     *
     * @param listener
     */
    public void setOnWindowCloseListener(IOnShareWindowCloseListener listener) {
        mOnWindowCloseListener = listener;
    }

    /**
     * 分享类型，用于埋点
     *
     * @param shareType
     */
    public void setShareType(int shareType) {
        mShareType = shareType;
    }

    /**
     * 设置分享数据
     *
     * @param data
     */
    public void setOneFriendModel(WechatModel data) {
        this.mOneFriendModel = data;
    }

    /**
     * 设置分享数据
     *
     * @param data
     */
    public void setFriendsModel(WechatModel data) {
        this.mFriendsModel = data;
    }

    private void init(final View webView, final int sourcetype, final Bitmap bitmap, final String path, final Bitmap friendCircle) {
        if (activity == null) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(MainApplicationLike.getAppContext());
        View view = inflater.inflate(R.layout.share_pop, null);

        pop = new PushFromBottomDialog(activity, view);
        if (bgAlpha > -1) {
            LogTools.e("SharePopWindow", "bgAlpha = " + bgAlpha);
            pop.getWindow().setDimAmount(bgAlpha);
        }
        shareFriend = (LinearLayout) view.findViewById(R.id.wx_share_friend);
        shareFriendCircle = (LinearLayout) view.findViewById(R.id.wx_share_friend_circle);

        shareFriend.setOnClickListener(new View.OnClickListener() {

            //分享给好友
            @Override
            public void onClick(View v) {
                if (sourcetype == SHARESOUCE_H5) {//h5 share call js
                    if (webView != null && webView instanceof WebView) {
                        ((WebView) webView).loadUrl("javascript:djappShareRes('" + 1 + "')");
                    }
                }
                if (!checkIsSupportShare()) {
                    closePop();
                    ShowTools.toast("您的微信版本过低或没有安装微信!");
                    return;
                }
                if (sourcetype == SHARESOUCE_PIC && bitmap != null) {//图片分享
                    mReq = WXHelper.instancePicWechat(bitmap);
                } else {
                    // 获得微信实例
                    if (!TextUtils.isEmpty(path)) {
                        mReq = WXHelper.instanceWechat(mOneFriendModel, true, path);
                    } else {
                        mReq = WXHelper.instanceWechat(mOneFriendModel, false, "");
                    }

                }
                if (mReq == null) {
                    return;
                }

                mReq.scene = SendMessageToWX.Req.WXSceneSession;


                MainApplicationLike.getInstance().getWXApi().sendReq(mReq);
                closePop();
            }
        });

        shareFriendCircle.setOnClickListener(new View.OnClickListener() {

            //分享到朋友圈
            @Override
            public void onClick(View v) {
                if (sourcetype == SHARESOUCE_H5) {//h5 share call js
                    if (webView != null && webView instanceof WebView) {
                        ((WebView) webView).loadUrl("javascript:djappShareRes('" + 2 + "')");
                    }
                }
                if (!checkIsSupportShare()) {
                    closePop();
                    ShowTools.toast("您的微信版本过低或没有安装微信!");
                    return;
                }
                if (sourcetype == SHARESOUCE_PIC && bitmap != null) {
                    //图片分享
                    mReq = WXHelper.instancePicWechat(bitmap);
                    if (mReq == null) {
                        return;
                    }

                    mReq.scene = SendMessageToWX.Req.WXSceneTimeline;
                    MainApplicationLike.getInstance().getWXApi().sendReq(mReq);
                    closePop();
                } else {
                    if (friendCircle != null) {
                        mReq = WXHelper.instancePicWechat(friendCircle);
                        if (mReq == null) {
                            return;
                        }

                        mReq.scene = SendMessageToWX.Req.WXSceneTimeline;
                        MainApplicationLike.getInstance().getWXApi().sendReq(mReq);
                        closePop();
                    } else {
                        if (TextUtils.isEmpty(mFriendsModel.getIconUrl())) {
                            mReq = WXHelper.instanceWechat(mFriendsModel, false, "");
                            if (mReq == null) {
                                return;
                            }

                            mReq.scene = SendMessageToWX.Req.WXSceneTimeline;
                            MainApplicationLike.getInstance().getWXApi().sendReq(mReq);
                            closePop();
                        } else {
//                            ImageLoader.loadImage(mFriendsModel.getIconUrl(), R.mipmap.icon,
//                                    new ImageSize(100, 100), new ImageLoadingListener() {
//
//                                        @Override
//                                        public void onLoadingStarted(String var1, View var2) {
//                                            // addProgressBar(view);
//                                        }
//
//                                        @Override
//                                        public void onLoadingFailed(String var1, View var2, FailReason var3) {
//                                        }
//
//                                        @Override
//                                        public void onLoadingComplete(String var1, View var2, Bitmap bitmap) {
//                                            // 分享出去
//                                            mFriendsModel.setIcon(bitmap);
//
//                                            // 获得微信实例
//                                            mReq = WXHelper.instanceWechat(mFriendsModel, false, "");
//                                            if (mReq == null) {
//                                                return;
//                                            }
//
//                                            mReq.scene = SendMessageToWX.Req.WXSceneTimeline;
//                                            MainApplicationLike.getsInstance().getWXApi().sendReq(mReq);
//                                            closePop();
//                                        }
//
//                                        @Override
//                                        public void onLoadingCancelled(String var1, View var2) {
//                                            // removeProgressBar(view);
//                                        }
//                                    });
                        }
                    }
                }
            }
        });

        Button cancle_wm = (Button) view.findViewById(R.id.wx_share_cancle);
        cancle_wm.setOnClickListener(new View.OnClickListener() {
            // 取消
            @Override
            public void onClick(View v) {
                closePop();
            }
        });

        pop.setOnDismissListener(new Dialog.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 回调窗口关闭
                if (mOnWindowCloseListener != null) {
                    mOnWindowCloseListener.onWindowClosed();
                }
            }
        });
    }

    public void showPop(View v) {
        if (pop != null && !pop.isShowing()) {
            pop.show();
        }
    }

    public void closePop() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    /**
     * 设置弹出Pop后背景变暗效果
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 检查是否有安装微信或微信版本过低不支持分享
     *
     * @return
     */
    private boolean checkIsSupportShare() {
        int wxSdkVersion = MainApplicationLike.getInstance().getWXApi().getWXAppSupportAPI();

        if (wxSdkVersion < 0x21020001) {
            return false;
        }

        return true;
    }

    /**
     * EventBus事件响应，关闭弹框
     * 3.4增加
     *
     * @param event
     */
    // @Subscribe
    public void onEventMainThread(CloseShareWindowEvent event) {
        closePop();
    }
}
