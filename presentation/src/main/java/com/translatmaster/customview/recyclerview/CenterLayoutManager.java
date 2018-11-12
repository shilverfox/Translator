package com.translatmaster.customview.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by zxp on 18/7/17.
 */

public class CenterLayoutManager extends LinearLayoutManager {
    /**
     * 是否需要放慢速度
     */
    private boolean mNeedSpeed;
    private Context mContext;
    private int rvtop;

    public CenterLayoutManager(Context context) {
        super(context);
        mContext = context;
    }

    public CenterLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mContext = context;
    }

    public CenterLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    public void setNeedSpeed(boolean needSpeed) {
        mNeedSpeed = needSpeed;
    }

    public void setRecyclerTop(int top) {
        rvtop = top;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        SmoothScroller linearSmoothScroller =
                new SmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(position);

        try {
            startSmoothScroll(linearSmoothScroller);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    class SmoothScroller extends LinearSmoothScroller {
        public SmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDyToMakeVisible(View view, int snapPreference) {
            final RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager == null || !layoutManager.canScrollVertically()) {
                return 0;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                    view.getLayoutParams();
            int top = layoutManager.getDecoratedTop(view) - params.topMargin;
            int bottom = layoutManager.getDecoratedBottom(view) + params.bottomMargin;
            int start = layoutManager.getPaddingTop();
            int end = layoutManager.getHeight() - layoutManager.getPaddingBottom();
            return calculateDtToFit(top, bottom, start, end - rvtop, snapPreference);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            if (mNeedSpeed) {
                return 150f / displayMetrics.densityDpi;
            } else {
                return super.calculateSpeedPerPixel(displayMetrics);
            }
        }
    }
}
