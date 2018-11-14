package com.jbsx.view.login.thirdapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.jbsx.app.MainApplicationLike;

/**
 * 第三方登录api跳转中心
 */

public class ThridPartyLoginHelper {

    /**
     * 微信登录
     *
     * @param context
     */
    public static void requestWeixinLogin(Context context) {
        if (context == null) {
            return;
        }

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "jdlogin";
        MainApplicationLike.getInstance().getWXApi().sendReq(req);
    }
}
