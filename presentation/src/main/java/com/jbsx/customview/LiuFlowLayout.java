package com.jbsx.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动换行的tag容器
 */
public class LiuFlowLayout extends ViewGroup {

    private int verticalSpacing;

    public LiuFlowLayout(Context context) {
        this(context, null);
    }

    public LiuFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiuFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setVerticalSpacing(int verticalSpacing){
        this.verticalSpacing = verticalSpacing;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                //child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                measureChild(child,widthMeasureSpec,heightMeasureSpec);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width;
                y = row * height + height;
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * height + height;
                }
            }
        }
        if (row > 0) {
            y += row * verticalSpacing;
        }
        // 设置容器所需的宽度和高度
        if (widthMode == MeasureSpec.AT_MOST && x < maxWidth) {
            maxWidth = Math.min(x, maxWidth);
        }
        setMeasuredDimension(maxWidth, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width;
                y = row * height + height;
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * height + height;
                }
                if (row > 0) {
                    y += row * verticalSpacing;
                }
                child.layout(x - width, y - height, x, y);
            }
        }

    }
}
