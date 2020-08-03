package com.jbsx.utils;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jbsx.R;
import com.jbsx.customview.nicedialog.BaseNiceDialog;
import com.jbsx.customview.nicedialog.NiceDialog;
import com.jbsx.customview.nicedialog.ViewConvertListener;
import com.jbsx.customview.nicedialog.ViewHolder;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 分享，使用第三方库
 * http://wiki.mob.com/sdk-share-android-3-0-0/
 *
 * Title:京剧史诗巨作《绝版赏析》APP正式上线！
 Url:http://jbsx.hy.china1904.com/jbsx/src/forapp/downLoad.html
 Text:“叙述旧闻，钩沉往事，重谭梨园秘辛，品味伶人甘苦！” 绝版唱片、珍贵史料、名流耆宿、嘉宾云集，《绝版赏析》APP甄选精编500集节目，展现京剧百年发展历程。
 ImageUrl:http://jbsx.hy.china1904.com/jbsx/commons/imgs/share.png
 */
public class ShareHelper {
    private final static String SHARE_URL = "http://jbsx.china1896.com/jbsx/src/forapp/downLoad.html";
    private static ShareHelper mInstance = new ShareHelper();
    private NiceDialog mNiceDialog;

    private ShareHelper() {
    }

    public static ShareHelper getInstance() {
        return mInstance;
    }

    private void doShare(Context context, String platform) {
        if (context == null) {
            return;
        }

        OnekeyShare oks = new OnekeyShare();

        if (platform != null) {
            oks.setPlatform(platform);
        }

        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("京剧史诗巨作《绝版赏析》APP正式上线！");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(SHARE_URL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("“叙述旧闻，钩沉往事，重谭梨园秘辛，品味伶人甘苦！” 绝版唱片、珍贵史料、名流耆宿、嘉宾云集，《绝版赏析》APP甄选精编500集节目，展现京剧百年发展历程。");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("http://jbsx.hy.china1904.com/jbsx/commons/imgs/share.png");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(SHARE_URL);
        // 启动分享GUI，不显示默认提供的dialog
        oks.show(context);
    }

    public void dismissShareDialog() {
        if (mNiceDialog != null) {
            mNiceDialog.dismiss();
        }
    }

    public void showShareDialog(final Context context, FragmentManager fragmentManager) {
        if (mNiceDialog == null) {
            mNiceDialog = NiceDialog.init();
        }

        mNiceDialog
                .setLayoutId(R.layout.share_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.wechat, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doShare(context, Wechat.NAME);
                            }
                        });

                        holder.setOnClickListener(R.id.wechat_pyq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doShare(context, WechatMoments.NAME);
                            }
                        });

                        holder.setOnClickListener(R.id.qq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doShare(context, QQ.NAME);
                            }
                        });

                        holder.setOnClickListener(R.id.weibo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doShare(context, SinaWeibo.NAME);
                            }
                        });
                    }
                })
                .setDimAmount(0.3f)
                .setShowBottom(true)
                .show(fragmentManager);
    }
}
