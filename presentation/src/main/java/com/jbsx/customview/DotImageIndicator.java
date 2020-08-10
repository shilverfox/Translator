package com.jbsx.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jbsx.R;


/**
 * 小圆点
 * 
 * @author Li Jian
 *
 */
public class DotImageIndicator extends LinearLayout {
    /**
     * 类型，区别是采用不同的资源文件
     */
    public final static int DOT_INDICATOR_TYPE_GREY = 0;
    public final static int DOT_INDICATOR_TYPE_GREE = 1;
	
	private Context mContext;

    private int mType = DOT_INDICATOR_TYPE_GREE;
	
	public DotImageIndicator(Context context) {
		super(context);
		
		mContext = context;
	}

    public void setType(int type) {
        mType = type;
    }
	
    public DotImageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public DotImageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        mContext = context;
    }

	/**
     * 处理图片数量显示
     * 
     * @param counts
     */
    public void initImageDot(int counts) {
        // 清空
        this.removeAllViews();

        // 只有一张图片则不显示
        if (counts <= 1) {
            return;
        }

        int marginRight = 0;
        int drawableId = 0;
        // 设置样式
        if (mType == DOT_INDICATOR_TYPE_GREY) {
            drawableId = R.drawable.commodity_dot;
            marginRight = 5;
        } else {
            drawableId = R.drawable.commodity_dot_1;
            marginRight = 14;
        }

        for (int i = 0; i < counts; i++) {
            ImageView img = new ImageView(mContext);

            img.setBackgroundResource(drawableId);
            img.setEnabled(false);

            LayoutParams lp = new LayoutParams(
            		LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, marginRight, 0);

            // 最后一个圆点无margin
            if (i != counts - 1) {
                img.setLayoutParams(lp);
            }

            this.addView(img);
        }

        // 默认第一个选中
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            if (view != null) {
                view.setEnabled(true);
            }
        }
    }

    /**
     * 设置选中小圆点颜色
     */
    public void updateImageDotStatus(int focused) {
        int counts = this.getChildCount();

        for (int i = 0; i < counts; i++) {
            View view = this.getChildAt(i);
            view.setEnabled(i == focused);
        }
    }
}
