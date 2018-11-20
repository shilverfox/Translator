package com.jbsx.player.util;

import com.app.domain.net.data.ConstData;
import com.jbsx.player.data.SingleVideoData;

public class SingleVideoUtil {
    /**
     * 拿到播放视频
     *
     * @return
     */
    public static String getVideoUrl(SingleVideoData data) {
        String url = "";
        if (data != null && data.getPayload() != null) {
            url = data.getPayload().getPlayUrl();
        }

        return url;
    }

    private static boolean isStandDefinition(String type) {
        return ConstData.VIDEO_DEFINITION_TYPE_STAND.equals(type);
    }

    private static boolean isHighDefinition(String type) {
        return ConstData.VIDEO_DEFINITION_TYPE_HIGH.equals(type);
    }

    /**
     * 是否为标清
     *
     * @param data
     * @return
     */
    public static boolean isStandDefinition(SingleVideoData data) {
        return isStandDefinition(getDefinitionType(data));
    }

    public static boolean isHighDefinition(SingleVideoData data) {
        return isHighDefinition(getDefinitionType(data));
    }

    public static String getDefinitionType(SingleVideoData data) {
        String type = "";
        if (data != null && data.getPayload() != null && data.getPayload().getParam() != null
                && data.getPayload().getParam().size() > 0) {
            type = data.getPayload().getParam().get(0).getType() + "";
        }

        return type;
    }
}
