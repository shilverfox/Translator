package com.jbsx.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/8/2.
 */

public class RoundCornerImageView extends android.support.v7.widget.AppCompatImageView {

    private int topLeftRadius;
    private int topRightRadius;
    private int bottomLeftRadius;
    private int bottomRightRadius;
    private boolean cornerEnabled = true;

    public RoundCornerImageView(Context context) {
        super(context);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setCornerRadii(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        if (topLeftRadius < 0) {
            topLeftRadius = 0;
        }
        if (topRightRadius < 0) {
            topRightRadius = 0;
        }
        if (bottomLeftRadius < 0) {
            bottomLeftRadius = 0;
        }
        if (bottomRightRadius < 0) {
            bottomRightRadius = 0;
        }
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
    }

    public void setCornerEnabled(boolean cornerEnabled) {
        this.cornerEnabled = cornerEnabled;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cornerEnabled) {
            cornerPath(canvas);
        }
        super.onDraw(canvas);
    }

    private void cornerPath(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        try {
            if (width > 0 && height > 0) {
                Path path = new Path();
                //起始位置
                if (topLeftRadius > 0) {
                    path.moveTo(topLeftRadius, 0);
                } else {
                    path.moveTo(0, 0);
                }

                //上边线
                if (topRightRadius > 0) {
                    path.lineTo(width - topRightRadius, 0);
                    RectF topRight = new RectF(width - topRightRadius * 2, 0, width, topRightRadius * 2);
                    path.arcTo(topRight, -90, 90);
                } else {
                    path.lineTo(width, 0);
                }

                //右边线
                if (bottomRightRadius > 0) {
                    path.lineTo(width, height - bottomRightRadius);
                    RectF bottomRight = new RectF(width - bottomRightRadius * 2, height - bottomRightRadius * 2, width, height);
                    path.arcTo(bottomRight, 0, 90);
                } else {
                    path.lineTo(width, height);
                }

                //下边线
                if (bottomLeftRadius > 0) {
                    path.lineTo(bottomLeftRadius, height);
                    RectF bottomLeft = new RectF(0, height - bottomLeftRadius * 2, bottomLeftRadius * 2, height);
                    path.arcTo(bottomLeft, 90, 90);
                } else {
                    path.lineTo(0, height);
                }

                //左边线
                if (topLeftRadius > 0) {
                    path.lineTo(0, topLeftRadius);
                    RectF topLeft = new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2);
                    path.arcTo(topLeft, 180, 90);
                } else {
                    path.lineTo(0, 0);
                }
                canvas.clipPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
