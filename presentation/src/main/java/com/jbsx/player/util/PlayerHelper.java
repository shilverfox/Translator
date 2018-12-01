package com.jbsx.player.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.jbsx.player.PlayerActivity;
import com.jbsx.player.data.PlayerData;
import com.jbsx.utils.Router;
import com.jbsx.view.login.util.LoginHelper;

/**
 * 跳转到视频播放页
 */
public class PlayerHelper {

    public static void gotoPlayer(Activity context, PlayerData playerData) {
        // 看视频需要登陆
        if (LoginHelper.getInstance().isLogin()) {
            if (playerData != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Router.PLAYER_REQUEST_KEY, playerData);

                Router.getInstance().open(PlayerActivity.class, context, bundle);
            }
        } else {
            LoginHelper.getInstance().showLoginDialog(context);
        }
    }

    /**
     * 造数据
     *
     * @param albumId
     * @param singId
     * @param definitionType
     * @return
     */
    public static PlayerData makePlayerData(String albumId, String singId, String definitionType) {
        PlayerData playerData = new PlayerData();
        playerData.setAlbumId(albumId);
        playerData.setSingleId(singId);
        playerData.setType(definitionType);

        return playerData;
    }
}
