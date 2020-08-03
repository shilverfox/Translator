package com.jbsx.customview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.jbsx.utils.UiTools;

public class RadiusBackgroundSpan extends ReplacementSpan {
    private static final int PADDING_Y = UiTools.dip2px(1.0F);
    private static final int PADDING_X = UiTools.dip2px(1.0F);
    private int mTextCount = -1;
    private int mSize;
    private int mBackGroundColor;
    private int mTextColor;
    private int mRadius;
    private float mTextSize;
    private int mOffsetY;

    public RadiusBackgroundSpan(int bgColor, int textColor, int radius) {
        this.mBackGroundColor = bgColor;
        this.mTextColor = textColor;
        this.mRadius = radius;
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setTextSize(this.mTextSize);
        this.mSize = (int)(paint.measureText(text, start, end) + (float)(2 * this.mRadius));
        if (this.needCalculateWidthByCount()) {
            this.mSize = (int)(paint.measureText("加") * (float)this.mTextCount + (float)(2 * this.mRadius));
        }

        this.mSize += 2 * PADDING_X;
        return this.mSize;
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color = paint.getColor();
        paint.setColor(this.mBackGroundColor);
        paint.setAntiAlias(true);
        RectF oval = new RectF(x, (float)y + paint.ascent() - (float)PADDING_Y - (float)this.mOffsetY, x + (float)this.mSize, (float)y + paint.descent() + (float)PADDING_Y - (float)this.mOffsetY);
        canvas.drawRoundRect(oval, (float)this.mRadius, (float)this.mRadius, paint);
        paint.setColor(this.mTextColor);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, start, end, oval.centerX(), (float)(y + 1 - this.mOffsetY), paint);
    }

    public void setTextSize(float size) {
        this.mTextSize = size;
    }

    public void setOffsetY(int offsetY) {
        this.mOffsetY = offsetY;
    }

    public void setWidthByTextCount(int textCount) {
        this.mTextCount = textCount;
    }

    private boolean needCalculateWidthByCount() {
        return this.mTextCount != -1;
    }
}

