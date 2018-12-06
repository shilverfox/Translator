package com.jbsx.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;

/**
 * 展示重新加载按钮
 */
public class ReloadBarHelper {
    public static int ERRO_PARENT_ID = -10001;

    private static View createReloadBar() {
        View LoadingErroBar = LayoutInflater.from(MainApplicationLike.getAppContext())
                .inflate(R.layout.reload, null);
        return LoadingErroBar;
    }

    public static void addReloadBar(final View content, final Runnable firstBtnClick) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addReloadBarInUiThread(content, firstBtnClick);
            }
        };
        MainApplicationLike.getInstance().getHanlder().post(runnable);
    }

    private static void initReloadBar(View LoadingErroBar, final Runnable firstBtnClick) {
        TextView btnFirst = LoadingErroBar.findViewById(R.id.tv_reload);
        if (firstBtnClick != null) {
            btnFirst.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    firstBtnClick.run();
                }
            });
        }
    }

    private static void addReloadBarInUiThread(View content, final Runnable firstBtnClick) {
        if (content == null) {
            return;
        }

        BarHelper.cleanAllBar(content);
        ViewGroup contentParent = (ViewGroup) content.getParent();
        if (contentParent == null) {
            return;
        }
        int index = contentParent.indexOfChild(content);
        contentParent.removeView(content);
        RelativeLayout layout = new RelativeLayout(content.getContext());// 定义框架布局器
        layout.setId(ERRO_PARENT_ID);
        contentParent.addView(layout, index, content.getLayoutParams());
        layout.addView(content, content.getLayoutParams());// 添加组件
        content.setVisibility(View.GONE);

        View reloadBar = createReloadBar();
        initReloadBar(reloadBar, firstBtnClick);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layout.addView(reloadBar, params);
    }

    public static void removeReloadBar(View content) {
        if (content == null) return;
        ViewGroup contentParent = (ViewGroup) content.getParent();
        if (contentParent != null && contentParent.getId() == ERRO_PARENT_ID) {
            contentParent.removeView(content);
            ViewGroup contentParentParent = (ViewGroup) contentParent.getParent();
            if (contentParentParent != null) {
                int index = contentParentParent.indexOfChild(contentParent);
                contentParentParent.removeView(contentParent);
                contentParentParent.addView(content, index,
                        contentParent.getLayoutParams());
                content.setVisibility(View.VISIBLE);
            }
        }
    }
}
