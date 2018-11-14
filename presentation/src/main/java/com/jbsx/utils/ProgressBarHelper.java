package com.jbsx.utils;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.JDProgressBar;

public class ProgressBarHelper {

    public static int ERRO_PARENT_ID = 10000;

    public static ProgressBar getLoadingProgressBar() {
        return new JDProgressBar(MainApplicationLike.getAppContext());
    }

    public static void addProgressBar(View content) {
        ProgressBar progressBar = getLoadingProgressBar();
        addProgressBar(content, false, false, progressBar, true);
    }

    public static void addProgressBar(View content, boolean isHiddenContent, boolean isblock) {
        ProgressBar progressBar = getLoadingProgressBar();
        addProgressBar(content, false, isHiddenContent, progressBar, false);
    }

    //添加进度条
    private static void addProgressBar(View content, boolean isHiddenProgressBar, boolean isHiddenContent, ProgressBar progressBar, boolean isblock) {
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
        RelativeLayout layout = new RelativeLayout(content.getContext());
        layout.setId(ERRO_PARENT_ID);
        contentParent.addView(layout, index, content.getLayoutParams());
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(content.getLayoutParams().width, content.getLayoutParams().height);
        lp1.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.setEnabled(true);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });
        layout.addView(content, lp1);//添加组件
        if (isblock) {
            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(content.getLayoutParams().MATCH_PARENT, content.getLayoutParams().MATCH_PARENT);
            RelativeLayout progressBarLayout = new RelativeLayout(content.getContext());
            progressBarLayout.addView(progressBar);
            progressBarLayout.setClickable(true);
            layout.addView(progressBarLayout, lp2);
        } else {
            layout.addView(progressBar);
        }
        if (isHiddenProgressBar) {
            progressBar.setVisibility(View.GONE);
        }
        if (isHiddenContent) {
            content.setVisibility(View.INVISIBLE);
        } else {
            content.setVisibility(View.VISIBLE);
        }
    }

    //移除进度条
    public static void removeProgressBar(View content) {
        if (content == null) {
            return;
        }
        ViewGroup contentParent = (ViewGroup) content.getParent();
        if (contentParent != null && contentParent.getId() == ERRO_PARENT_ID) {
            contentParent.removeView(content);
            ViewGroup contentParentParent = (ViewGroup) contentParent.getParent();
            if (contentParentParent != null) {
                int index = contentParentParent.indexOfChild(contentParent);
                contentParentParent.removeView(contentParent);
                contentParentParent.addView(content, index, contentParent.getLayoutParams());
            }
            content.setVisibility(View.VISIBLE);
        }

    }

    @Deprecated
    public static void removeProgressBarInThread(final View content) {
        MainApplicationLike.getInstance().getHanlder().post(new Runnable() {
            @Override
            public void run() {
                ProgressBarHelper.removeProgressBar(content);
            }
        });
    }

    @Deprecated
    public static void addProgressBarInThread(final View content) {
        MainApplicationLike.getInstance().getHanlder().post(new Runnable() {
            @Override
            public void run() {
                addProgressBar(content);
            }
        });
    }

}
