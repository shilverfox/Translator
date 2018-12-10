package com.jbsx.utils;

import android.content.Context;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 分享，使用第三方库
 * http://wiki.mob.com/sdk-share-android-3-0-0/
 *
 * Title:京剧史诗巨作《绝版赏析》APP正式上线！
 Url:http://jbsx.china1896.com/jbsx/src/forapp/downLoad.html
 Text:“叙述旧闻，钩沉往事，重谭梨园秘辛，品味伶人甘苦！” 绝版唱片、珍贵史料、名流耆宿、嘉宾云集，《绝版赏析》APP甄选精编500集节目，展现京剧百年发展历程。
 ImageUrl:http://jbsx.china1896.com/jbsx/commons/imgs/share.png
 */
public class ShareHelper {
    private final static String SHARE_URL = "http://jbsx.china1896.com/jbsx/src/forapp/downLoad.html";

    public static void showShare(Context context) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("京剧史诗巨作《绝版赏析》APP正式上线！");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(SHARE_URL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("“叙述旧闻，钩沉往事，重谭梨园秘辛，品味伶人甘苦！” 绝版唱片、珍贵史料、名流耆宿、嘉宾云集，《绝版赏析》APP甄选精编500集节目，展现京剧百年发展历程。");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("http://jbsx.china1896.com/jbsx/commons/imgs/share.png");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(SHARE_URL);
        // 启动分享GUI
        oks.show(context);
    }
}
