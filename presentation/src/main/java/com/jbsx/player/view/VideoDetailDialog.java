package com.jbsx.player.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.PushFromBottomDialog;
import com.jbsx.data.AppConstData;
import com.jbsx.player.data.SpecialSingleData;
import com.jbsx.player.util.AlbumDetailUtil;
import com.jbsx.utils.UiTools;
import com.jbsx.view.main.entity.Celebrities;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频简介
 */
public class VideoDetailDialog {
    private Context mContext;

    /** 片库详情信息 */
    private SpecialSingleData mSpecialSingleData;

    /**
     * 视屏详情对话框
     */
    private PushFromBottomDialog mSingleDetailDialog;

    public VideoDetailDialog(Context context, SpecialSingleData singleData) {
        mContext = context;
        mSpecialSingleData = singleData;
    }

    public void showDetailDialog() {
        if (mContext == null || mSpecialSingleData == null) {
            return;
        }

        View dialogView = LayoutInflater.from(MainApplicationLike.getAppContext())
                .inflate(R.layout.single_video_detail_info_view, null);

        TextView title = dialogView.findViewById(R.id.dialog_album_detail_title);
        TextView crew = dialogView.findViewById(R.id.dialog_album_detail_crew);
        TextView summary = dialogView.findViewById(R.id.dialog_album_detail_summary);
        ImageView close = dialogView.findViewById(R.id.dialog_album_detail_close);
        HorizontalScrollView hsv = dialogView.findViewById(R.id.hsv_album_detail_crew);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSingleDetailDialog.dismiss();
            }
        });

        title.setText(AlbumDetailUtil.getTitle(mSpecialSingleData));
        crew.setText(AlbumDetailUtil.getCrew(mSpecialSingleData, true));
        summary.setText(AlbumDetailUtil.getIntroduce(mSpecialSingleData));

        // 横向滑动演职员表
        hsv.removeAllViews();
        hsv.addView(getCrewViewByType());

        mSingleDetailDialog = new PushFromBottomDialog(mContext, dialogView);
        mSingleDetailDialog.setSize(WindowManager.LayoutParams.MATCH_PARENT, UiTools.dip2px(350));
        mSingleDetailDialog.show();
    }

    private LinearLayout getCrewViewByType() {
        LinearLayout rootView = new LinearLayout(mContext);
        List<View> views = getCrewViews(mSpecialSingleData);
        for (View view : views) {
            rootView.addView(view);
        }

        return rootView;
    }

    private List<View> getCrewViews(SpecialSingleData data) {
        List<View> allViews = new ArrayList<>();
        List<Celebrities> celebrities = AlbumDetailUtil.getCelebrities(data);
        StringBuffer sb = new StringBuffer();

        int[] crewData = AppConstData.CREW_ALL;
        for(int i = 0; i < crewData.length; i++) {
            // 对应类型的职员列表
            StringBuffer memberOfType = AlbumDetailUtil.getScrewByType(celebrities, crewData[i]);
            if (memberOfType != null && memberOfType.length() > 0) {
                View itemView = getCrewViewItem(crewData[i], memberOfType.toString());
                if (itemView != null) {

                    if (i != 0) {
                       // 增加分割空白
                       View space = new View(mContext);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiTools.dip2px(15), LinearLayout.LayoutParams.MATCH_PARENT);
                        space.setLayoutParams(params);
                        allViews.add(space);
                    }

                    allViews.add(itemView);
                }
            }
        }

        return allViews;
    }

    private View getCrewViewItem(int type, String content) {
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.player_detail_crew_item, null);

            ImageView icon = view.findViewById(R.id.iv_crew_icon);
            TextView typeName = view.findViewById(R.id.tv_crew_type_name);
            TextView members = view.findViewById(R.id.tv_crew_names);

            int iconId = AlbumDetailUtil.getTypeIcon(type);
            if (iconId != -1) {
                icon.setImageResource(iconId);
            }

            String name = AlbumDetailUtil.getTypeName(type);
            if (!TextUtils.isEmpty(name)) {
                typeName.setText(AlbumDetailUtil.getTypeName(type));
            }

            if (!TextUtils.isEmpty(content)) {
                members.setText(content);
            }
        }

        return view;
    }
}
