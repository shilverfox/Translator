package com.jbsx.player.util;

import android.text.TextUtils;

import com.jbsx.data.AppConstData;
import com.jbsx.player.data.SpecialSingleData;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.SpecialSingles;

import java.util.List;

public class AlbumDetailUtil {
    public static SpecialSingles getSpecialSingle(SpecialSingleData data) {
        SpecialSingles singles = null;
        if (data != null && data.getPayload() != null) {
            singles = data.getPayload().getSpecialSingle();
        }

        return singles;
    }

    public static Single getSingle(SpecialSingleData data) {
        SpecialSingles singles = getSpecialSingle(data);
        Single single = null;
        if (singles != null) {
            single = singles.getSingle();
        }

        return single;
    }

    public static List<Celebrities> getCelebrities(SpecialSingleData data) {
        SpecialSingles singles = getSpecialSingle(data);
        List<Celebrities> celebrities = null;
        if (singles != null) {
            celebrities = singles.getCelebrities();
        }

        return celebrities;
    }

    /**
     * 找到剧组中的角色
     *
     * @param celebrities
     * @param type
     * @return
     */
    public static StringBuffer getScrewByType(List<Celebrities> celebrities, int type) {
        StringBuffer sb = new StringBuffer();

        if (celebrities != null) {
            for (int i = 0; i < celebrities.size(); i++) {
                Celebrities cel = celebrities.get(i);

                if (cel.getType() ==  type) {
                    // 多个人名用逗号分隔
                    if (sb.length() != 0) {
                        sb.append(",");
                    }

                    sb.append(cel.getName());
                }
            }
        }

        return sb;
    }

    private static int[] getCrewData(boolean showAllCrew) {
        return showAllCrew ? AppConstData.CREW_ALL : AppConstData.CREW_MAIN;
    }

    /**
     * 显示剧组列表
     *
     * @param data
     * @param showAllCrew 是否显示全剧组
     * @return
     */
    public static String getCrew(SpecialSingleData data, boolean showAllCrew) {
        List<Celebrities> celebrities = getCelebrities(data);
        StringBuffer sb = new StringBuffer();

        int[] crewData = getCrewData(showAllCrew);
        for(int i = 0; i < crewData.length; i++) {
            // 对应类型的职员列表
            StringBuffer memberOfType = getScrewByType(celebrities, crewData[i]);
            if (memberOfType != null && memberOfType.length() > 0) {
                // 有内容才拼接
                sb.append(getTypeName(crewData[i]));
                sb.append(memberOfType);

                if (i != crewData.length - 1) {
                    sb.append("    ");
                }
            }
        }

        return sb.toString();
    }

    /**
     * type翻译成中文名称
     *
     * @param type
     * @return
     */
    public static String getTypeName(int type) {
        switch (type) {
            case AppConstData.CELEBRITY_TYPE_DAOYAN:
                return "导演：";
            case AppConstData.CELEBRITY_TYPE_ZHUCHI:
                return "主持人：";
            case AppConstData.CELEBRITY_TYPE_ZHUANGAO:
                return "撰稿人：";
            case AppConstData.CELEBRITY_TYPE_ZHUZHE:
                return "作者：";
            case AppConstData.CELEBRITY_TYPE_JIESHUO:
                return "解说：";
            case AppConstData.CELEBRITY_TYPE_ZHUJIANG:
                return "主讲嘉宾：";
            case AppConstData.CELEBRITY_TYPE_ZRBJ:
                return "责任编辑：";
            case AppConstData.CELEBRITY_TYPE_ZONGBJ:
                return "总编辑：";
            case AppConstData.CELEBRITY_TYPE_XSYSGW:
                return "艺术顾问：";
            case AppConstData.CELEBRITY_TYPE_ZONGGW:
                return "总顾问：";
            case AppConstData.CELEBRITY_TYPE_JIANZHI:
                return "监制：";
            case AppConstData.CELEBRITY_TYPE_ZONGJIANZHI:
                return "总监制：";
            default:
                return "";
        }
    }

    public static String getTitle(SpecialSingleData data) {
        String result = "";
        Single single = getSingle(data);
        if (single != null) {
            result = single.getTitle();
        }

        return result;
    }

    public static String getCount(SpecialSingleData data) {
        String result = "0";
        Single single = getSingle(data);
        if (single != null) {
            result = single.getPlayNum() + "";
        }

        return result + "次播放";
    }

    /**
     * 是否收藏
     * 非空即为关注
     *
     * @param data
     * @return
     */
    public static boolean isFavorite(SpecialSingleData data) {
        boolean result = false;
        Single single = getSingle(data);
        if (single != null) {
            result = !TextUtils.isEmpty(single.getIsCollect());
        }

        return result;
    }

    public static String getIntroduce(SpecialSingleData data) {
        String result = "";
        Single single = getSingle(data);
        if (single != null) {
            result = single.getIntroduce();
        }

        return result;
    }
}
