package com.jbsx.weixin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.jbsx.R;
import com.jbsx.utils.LogTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.weixin.data.IOnShareWindowCloseListener;
import com.jbsx.weixin.data.WechatModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WXHelper {
    /**
     * H5页面返回未定义字段的默认值
     */
    private final static String DEFAULT_H5_NO_DEFINED = "undefined";

    /**
     * 分享范围：好友
     */
    public final static int WX_SHARE_RANGE_A_FRIEND = 1;

    /**
     * 分享范围：朋友圈
     */
    public final static int WX_SHARE_RANGE_SOME_FRIENDS = 2;


    /**
     * 分享来源：webView
     */
    public final static int WX_SHARE_TYPE_WEB = 6;

    public static final int MAX_SIZE = 32768;
    public static final int MAX_SIZE_PIC = 10 * 1024 * 1024;
    public static final int MAX_SIZE_XCX = 128 * 1024;

    private static final String TAG = "WXHelper";

    /**
     * 分享方法
     * 3.4修改
     *
     * @param activity
     * @param view
     * @param thumb
     * @param shareUrl
     * @param title
     * @param describe
     * @param shareType
     * @param callback
     */
    public static void share(Activity activity, View view, Bitmap thumb, String shareUrl,
                             String title, String describe, int shareType,
                             IOnShareWindowCloseListener callback, String path) {
        share(activity, view, thumb, shareUrl, title, describe, shareType, callback, SharePopWindow.SHARESOUCE_COMMON, path);
    }

    public static void share(Activity activity, View view, Bitmap thumb, String shareUrl,
                             String title, String describe, int shareType,
                             IOnShareWindowCloseListener callback, int sourcetype, String path) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (thumb == null || getBitmapSize(thumb) <= MAX_SIZE) {
            LogTools.e(TAG, "not compress ");
            showSharePopWindow(activity, view, thumb, shareUrl, title, describe, shareType, callback, sourcetype, path);
            removeProgressBar(view);
        } else {
            LogTools.e(TAG, "compress ");
            new CompressBitmapTask(activity, view, thumb, shareUrl, title, describe, shareType, callback, sourcetype, path).execute(thumb);
        }

    }

    public static void shareNew(Activity activity, View view, Bitmap thumb, String iconUrl, String shareUrl,
                                String title, String describe, int shareType,
                                IOnShareWindowCloseListener callback, int sourcetype, String path) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        LogTools.e(TAG, "not compress ");
        showSharePopWindowNew(activity, view, thumb, iconUrl, shareUrl, title, describe, shareType, callback, sourcetype, 0.5f, path);
        removeProgressBar(view);
    }

    public static void sharePic(Activity activity, View view, Bitmap bitmap) {
        sharePic(activity, view, bitmap, -1);
    }

    public static void sharePic(Activity activity, View view, Bitmap bitmap, float bgAlpha) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (bitmap == null || getBitmapSize(bitmap) <= MAX_SIZE_PIC) {
            LogTools.e(TAG, "not compress ");
            showSharePopWindow(activity, view, bitmap, SharePopWindow.SHARESOUCE_PIC, bgAlpha, "");
            removeProgressBar(view);
        } else {
            LogTools.e(TAG, "compress ");
            new CompressBitmapTask(activity, view, SharePopWindow.SHARESOUCE_PIC, bgAlpha).execute(bitmap);
        }
    }

    private static void showSharePopWindow(Activity activity, View view, Bitmap thumb, String shareUrl,
                                           String title, String describe, int shareType,
                                           IOnShareWindowCloseListener callback, int sourcetype, float bgAlpha, String path) {
        // 创建分享数据
        WechatModel oneFriend = generateWechatModel(activity, title, describe, shareUrl, thumb,
                shareType, WX_SHARE_RANGE_A_FRIEND, path);

        WechatModel friends = generateWechatModel(activity, title, describe, shareUrl, thumb,
                shareType, WX_SHARE_RANGE_SOME_FRIENDS, "");
        SharePopWindow mSharePopWindow = null;
        if (sourcetype == SharePopWindow.SHARESOUCE_H5 || sourcetype == SharePopWindow.SHARESOUCE_PIC) {
            mSharePopWindow = new SharePopWindow(activity, view, sourcetype, thumb, bgAlpha, path);
        } else {
            mSharePopWindow = new SharePopWindow(activity, bgAlpha, path);
        }
        mSharePopWindow.setOneFriendModel(oneFriend);
        mSharePopWindow.setFriendsModel(friends);
        mSharePopWindow.setShareType(shareType);

        // 窗口关闭事件回调
        mSharePopWindow.setOnWindowCloseListener(callback);
        if (activity != null && !activity.isFinishing()) {
            mSharePopWindow.showPop(view);
        }
    }


    private static void showSharePopWindowNew(Activity activity, View view, Bitmap thumb, String iconUrl, String shareUrl,
                                              String title, String describe, int shareType,
                                              IOnShareWindowCloseListener callback, int sourcetype, float bgAlpha, String path) {
        // 创建分享数据
        WechatModel oneFriend = generateWechatModel(activity, title, describe, shareUrl, thumb,
                shareType, WX_SHARE_RANGE_A_FRIEND, path);

        WechatModel friends = generateWechatModel(activity, title, describe, shareUrl, thumb,
                shareType, WX_SHARE_RANGE_SOME_FRIENDS, "");
        friends.setIconUrl(iconUrl);
        SharePopWindow mSharePopWindow = null;
        if (sourcetype == SharePopWindow.SHARESOUCE_H5 || sourcetype == SharePopWindow.SHARESOUCE_PIC) {
            mSharePopWindow = new SharePopWindow(activity, view, sourcetype, thumb, bgAlpha, path);
        } else {
            mSharePopWindow = new SharePopWindow(activity, bgAlpha, path);
        }
        mSharePopWindow.setOneFriendModel(oneFriend);
        mSharePopWindow.setFriendsModel(friends);
        mSharePopWindow.setShareType(shareType);

        // 窗口关闭事件回调
        mSharePopWindow.setOnWindowCloseListener(callback);
        if (activity != null && !activity.isFinishing()) {
            mSharePopWindow.showPop(view);
        }
    }

    private static void showSharePopWindowNewXCX(Activity activity, View view, Bitmap thumb, String iconUrl, String shareUrl,
                                                 String title, String describe, int shareType,
                                                 IOnShareWindowCloseListener callback, int sourcetype, float bgAlpha, String path, Bitmap friendCirlceBitmap) {
        // 创建分享数据
        WechatModel oneFriend = generateWechatModel(activity, title, describe, shareUrl, thumb,
                shareType, WX_SHARE_RANGE_A_FRIEND, path);

        WechatModel friends = generateWechatModel(activity, title, describe, shareUrl, thumb,
                shareType, WX_SHARE_RANGE_SOME_FRIENDS, "");
        friends.setIconUrl(iconUrl);
        SharePopWindow mSharePopWindow = null;
        if (sourcetype == SharePopWindow.SHARESOUCE_H5 || sourcetype == SharePopWindow.SHARESOUCE_PIC) {
            mSharePopWindow = new SharePopWindow(activity, view, sourcetype, thumb, bgAlpha, path, friendCirlceBitmap);
        } else {
            mSharePopWindow = new SharePopWindow(activity, bgAlpha, path);
        }
        mSharePopWindow.setOneFriendModel(oneFriend);
        mSharePopWindow.setFriendsModel(friends);
        mSharePopWindow.setShareType(shareType);

        // 窗口关闭事件回调
        mSharePopWindow.setOnWindowCloseListener(callback);
        if (activity != null && !activity.isFinishing()) {
            mSharePopWindow.showPop(view);
        }
    }

    private static void showSharePopWindow(Activity activity, View view, Bitmap thumb, String shareUrl,
                                           String title, String describe, int shareType,
                                           IOnShareWindowCloseListener callback, int sourcetype, String path) {
        showSharePopWindow(activity, view, thumb, shareUrl, title, describe, shareType, callback, sourcetype, -1, path);

    }

    private static void showSharePopWindow(Activity activity, View view, Bitmap bitmap, int sourcetype, String path) {
        showSharePopWindow(activity, view, bitmap, sourcetype, -1, path);
    }

    private static void showSharePopWindow(Activity activity, View view, Bitmap bitmap, int sourcetype, float bgAlpha, String path) {

        SharePopWindow mSharePopWindow = null;
        mSharePopWindow = new SharePopWindow(activity, view, sourcetype, bitmap, bgAlpha, path);

        // 窗口关闭事件回调
        if (activity != null && !activity.isFinishing()) {
            mSharePopWindow.showPop(view);
        }
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static Bitmap bytes2Bitmap(byte[] b) {
        if (b != null && b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    /**
     * 分享的图片压缩到限定大小
     */
    private static class CompressBitmapTask extends AsyncTask<Bitmap, Void, Bitmap> {
        Activity activity;
        View view;
        Bitmap thumb;
        String shareUrl;
        String title;
        String describe;
        int shareType;
        IOnShareWindowCloseListener callback;
        int sourcetype;
        float bgAlpha = -1;
        String path;

        public CompressBitmapTask(Activity activity, View view, Bitmap thumb, String shareUrl,
                                  String title, String describe, int shareType,
                                  IOnShareWindowCloseListener callback, int sourcetype, String path) {
            this.activity = activity;
            this.view = view;
            this.thumb = thumb;
            this.shareUrl = shareUrl;
            this.title = title;
            this.describe = describe;
            this.shareType = shareType;
            this.callback = callback;
            this.sourcetype = sourcetype;
            this.path = path;
        }

        public CompressBitmapTask(Activity activity, View view, int sourcetype) {
            this(activity, view, sourcetype, -1);
        }

        public CompressBitmapTask(Activity activity, View view, int sourcetype, float bgAlpha) {
            this.activity = activity;
            this.view = view;
            this.sourcetype = sourcetype;
            this.bgAlpha = bgAlpha;
        }

        protected void onPreExecute() {
//            DLog.d(TAG,"start----- size "+getBitmapSize(thumb));
        }

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            ByteArrayOutputStream output = null;
            Bitmap thumBitmap = null;
            try {
                output = new ByteArrayOutputStream();
                bitmaps[0].compress(Bitmap.CompressFormat.PNG, 100, output);
                int options = 100;
                while (output.toByteArray().length > MAX_SIZE && options != 10) {
                    output.reset();
                    bitmaps[0].compress(Bitmap.CompressFormat.JPEG, options, output);
                    options -= 10;
                    LogTools.e(TAG, "ing----- size ");
                }
                byte[] result = output.toByteArray();
                LogTools.e(TAG, "end----- size " + result.length);
                thumBitmap = bytes2Bitmap(result);
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return thumBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            showSharePopWindow(activity, view, result, shareUrl, title, describe, shareType, callback, sourcetype, bgAlpha, path);
            removeProgressBar(view);
        }

    }


    /**
     * 分享的图片压缩到限定大小
     */
    private static class CompressBitmapTaskNew extends AsyncTask<Bitmap, Void, Bitmap> {
        Activity activity;
        View view;
        Bitmap thumb;
        String shareUrl;
        String title;
        String describe;
        int shareType;
        IOnShareWindowCloseListener callback;
        int sourcetype;
        float bgAlpha = -1;
        String path;

        public CompressBitmapTaskNew(Activity activity, View view, Bitmap thumb, String shareUrl,
                                     String title, String describe, int shareType,
                                     IOnShareWindowCloseListener callback, int sourcetype, String path) {
            this.activity = activity;
            this.view = view;
            this.thumb = thumb;
            this.shareUrl = shareUrl;
            this.title = title;
            this.describe = describe;
            this.shareType = shareType;
            this.callback = callback;
            this.sourcetype = sourcetype;
            this.path = path;
        }

        public CompressBitmapTaskNew(Activity activity, View view, int sourcetype) {
            this(activity, view, sourcetype, -1);
        }

        public CompressBitmapTaskNew(Activity activity, View view, int sourcetype, float bgAlpha) {
            this.activity = activity;
            this.view = view;
            this.sourcetype = sourcetype;
            this.bgAlpha = bgAlpha;
        }

        protected void onPreExecute() {
        }

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            ByteArrayOutputStream output = null;
            Bitmap thumBitmap = null;
            try {
                output = new ByteArrayOutputStream();
                bitmaps[0].compress(Bitmap.CompressFormat.PNG, 100, output);
                int options = 100;
                while (output.toByteArray().length > MAX_SIZE_XCX && options != 10) {
                    output.reset();
                    bitmaps[0].compress(Bitmap.CompressFormat.JPEG, options, output);
                    options -= 10;
                    LogTools.e(TAG, "ing----- size ");
                }
                byte[] result = output.toByteArray();
                LogTools.e(TAG, "end----- size " + result.length);
                thumBitmap = bytes2Bitmap(result);
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return thumBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            showSharePopWindow(activity, view, result, shareUrl, title, describe, shareType, callback, sourcetype, bgAlpha, path);
            removeProgressBar(view);
        }
    }


    /**
     * 移除通用Loading
     *
     * @param view
     */
    private static void removeProgressBar(View view) {
        if (view != null) {
            ProgressBarHelper.removeProgressBar(view);
        }
    }

    /**
     * loading
     *
     * @param view
     */
    private static void addProgressBar(View view) {
        if (view != null) {
            ProgressBarHelper.addProgressBar(view);
        }
    }

    /**
     * 创建微信分享对象
     *
     * @param title
     * @param description
     * @param url
     * @param icon
     * @param shareType
     * @param shareRange
     * @return
     */
    private static WechatModel generateWechatModel(Activity activity, String title,
                                                   String description, String url,
                                                   Bitmap icon, int shareType,
                                                   int shareRange, String path) {

        WechatModel model = new WechatModel();
        model.setTitle(handleTitle(title, shareType, shareRange));
        model.setDescription(handleDescription(description, shareType));
        model.setIcon(handleIcon(activity, icon));
        model.setShareUrl(url);
        model.setPath(path);


        return model;
    }


    private static String buildTransaction(String type) {
        return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : (type + System.currentTimeMillis());
    }

    /**
     * 根据分享类型获得标题
     * 3.4增加
     *
     * @param title
     * @param shareType  页面来源
     * @param shareRange 分享范围
     */
    private static String handleTitle(String title, int shareType, int shareRange) {
        // 为空时采用降级方案
        boolean isEmpty = TextUtils.isEmpty(title) || DEFAULT_H5_NO_DEFINED.equals(title);

        if (isEmpty) {
            switch (shareType) {
                case WX_SHARE_TYPE_WEB:
                    title = "京东到家";
                    break;
                default:
                    title = "京东到家";
                    break;
            }
        }

        return title;
    }

    /**
     * 根据分享类型获得描述
     * 3.4增加
     *
     * @param description
     * @param shareType
     */
    private static String handleDescription(String description, int shareType) {
        boolean isEmpty = TextUtils.isEmpty(description) || DEFAULT_H5_NO_DEFINED.equals(description);

        switch (shareType) {
            case WX_SHARE_TYPE_WEB:
                if (isEmpty) {
                    description = "我在京东到家发现了一个活动，赶快来围观啊！";
                }
                break;
            default:
                if (isEmpty) {
                    description = "京东到家，超值促销活动，优惠多多，抢购了！";
                }
                break;
        }

        return description;
    }

    /**
     * 将透明的bitmap变成白色
     *
     * @return
     */
    public static Bitmap setBitmapBgToWhite(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);

        return newBitmap;
    }

    /**
     * 分享logo
     *
     * @param context
     * @param icon
     * @return
     */
    private static Bitmap handleIcon(Activity context, Bitmap icon) {
        if (context == null) {
            return null;
        }

        if (icon == null) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_share_icon);
        }

        // 微信sdk处理透明，因此需要透明变白底
        icon = setBitmapBgToWhite(icon);

        return icon;
    }

    /**
     * 创建微信对象
     */
    public static SendMessageToWX.Req instanceWechat(WechatModel model, boolean isXCX, String path) {
        if (model == null) {
            return null;
        }
        SendMessageToWX.Req req = null;
        if (isXCX) {
            // 支持小程序的分享
            WXMiniProgramObject object = new WXMiniProgramObject();
            object.webpageUrl = model.getShareUrl();
            object.userName = "gh_5103b94a8a56";
            object.path = path;
            WXMediaMessage mediaMessage = new WXMediaMessage(object);
            mediaMessage.title = model.getTitle();
            mediaMessage.description = model.getDescription();
            mediaMessage.setThumbImage(model.getIcon());

            req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("miniProgram");
            req.message = mediaMessage;
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            // 普通扫码分享
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = model.getShareUrl();
            WXMediaMessage msg = new WXMediaMessage(webpage);

            msg.title = model.getTitle();
            msg.description = model.getDescription();
            msg.setThumbImage(model.getIcon());

            req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
        }

        return req;
    }

    public static SendMessageToWX.Req instancePicWechat(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;

        return req;
    }
}
