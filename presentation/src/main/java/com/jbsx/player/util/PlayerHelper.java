package com.jbsx.player.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.app.domain.net.data.ConstData;
import com.jbsx.player.PlayerActivity;
import com.jbsx.player.data.PlayerData;
import com.jbsx.utils.Router;
import com.jbsx.view.login.callback.IOnLoginListener;
import com.jbsx.view.login.util.LoginHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 跳转到视频播放页
 */
public class PlayerHelper {

    private static void handleGotoPlayer(Activity context, PlayerData playerData) {
        if (playerData != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Router.PLAYER_REQUEST_KEY, playerData);

            Router.getInstance().open(PlayerActivity.class, context, bundle);
        }
    }

    public static void gotoPlayer(final Activity context, final PlayerData playerData) {
        // 看视频需要登陆
        if (LoginHelper.getInstance().isLogin()) {
            handleGotoPlayer(context, playerData);
        } else {
            LoginHelper.getInstance().showLoginDialog(context, new IOnLoginListener() {
                @Override
                public void onSucess() {
                    handleGotoPlayer(context, playerData);
                }

                @Override
                public void onFailed() {

                }
            });
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

    /**
     * 清晰度排序，标清在先
     */
    public static LinkedHashMap<String, String> sortDefinition(LinkedHashMap<String, String> definitionList) {
        if (definitionList == null || definitionList.isEmpty()) {
            return null;
        }

        //先转成ArrayList集合
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(definitionList.entrySet());

        //从小到大排序（从大到小将o1与o2交换即可）
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {

            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (ConstData.VIDEO_DEFINITION_TYPE_NAME_STAND.equals(o1.getKey())
                        && ConstData.VIDEO_DEFINITION_TYPE_NAME_HIGH.equals(o2.getKey())) ? -1 : 1;
            }

        });

        //新建一个LinkedHashMap，把排序后的List放入
        LinkedHashMap<String, String> map2 = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : list) {
            map2.put(entry.getKey(), entry.getValue());
        }

        return map2;
    }
}
