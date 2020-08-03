package com.jbsx.player.util;

import android.text.TextUtils;

import com.app.domain.net.data.ConstData;
import com.jbsx.player.data.SingleVideoData;

import java.text.DecimalFormat;

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

    /**
     * 当前视频播放进度
     *
     * @param data
     * @return
     */
    public static long getSecond(SingleVideoData data) {
        long second = 0;
        if (data != null && data.getPayload() != null) {
            second = data.getPayload().getSecond();
        }

        return second;
    }

    /**
     * 是否收藏了视频
     *
     * @param data
     * @return
     */
    public static boolean isFavorite(SingleVideoData data) {
        boolean result = false;
        if (data != null && data.getPayload() != null) {
            result = !TextUtils.isEmpty(data.getPayload().getIsCollect());
        }

        return result;
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

    /**
     * 计算进度百分比
     *
     * @param currentPosition
     * @param totalLength
     * @return
     */
    public static String getWatchPercent(float currentPosition, float totalLength) {
        float percent = 0;
        String format = "0.0";

        try {
            percent = (currentPosition / totalLength) * 100;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        DecimalFormat decimalFormat = new DecimalFormat(format);
        String percentString = decimalFormat.format(percent);

        if (format.equals(percentString)) {
            // 0.0 数据直接返回0
            percentString = "0";
        }

        return percentString;
    }
}
