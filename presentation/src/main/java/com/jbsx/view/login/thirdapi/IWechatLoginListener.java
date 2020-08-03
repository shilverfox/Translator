package com.jbsx.view.login.thirdapi;

import com.jbsx.view.login.data.AccountVerifyEvent;
import com.jbsx.weixin.WechatEvent;

/**
 * 微信登录需要实现的方法
 *
 */

public interface IWechatLoginListener {
    /**
     * 获得微信sdk返回的用户授权结果
     * @param event
     */
    void onEventMainThread(LoginThirdResultEvent event);

    /**
     * 微信账号被风控后执行风控h5后的返回结果
     * @param event
     */
    void onEventMainThread(AccountVerifyEvent event);

    /**
     * 微信未登录状态下的操作信息
     * @param event
     */
    void onEventMainThread(WechatEvent event);
}
