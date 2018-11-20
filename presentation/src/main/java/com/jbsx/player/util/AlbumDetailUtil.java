package com.jbsx.player.util;

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

    public static String getScrew(SpecialSingleData data) {
        List<Celebrities> celebrities = getCelebrities(data);
        StringBuffer sb = new StringBuffer();
        if (celebrities != null) {
            for (Celebrities cel : celebrities) {
                sb.append(cel.getName());
            }
        }

        return sb.toString();
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

    public static String getIntroduce(SpecialSingleData data) {
        String result = "";
        Single single = getSingle(data);
        if (single != null) {
            result = single.getIntroduce();
        }

        return result;
    }
}
