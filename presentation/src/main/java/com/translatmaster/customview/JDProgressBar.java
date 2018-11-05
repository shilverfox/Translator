package com.translatmaster.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.translatmaster.R;
import com.translatmaster.utils.UiTools;


public class JDProgressBar extends ProgressBar {

	public JDProgressBar(Context context) {
		super(context);
		init();
	}
	
	public JDProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public JDProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		final LayoutParams layoutParams = new LayoutParams(UiTools.dip2px(34), UiTools.dip2px(34));
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.setLayoutParams(layoutParams);
		this.setBackgroundResource(R.drawable.progress_load_logo);
		this.setIndeterminateDrawable(this.getResources().getDrawable(R.drawable.progress_small));
		this.setIndeterminate(true);
		
	}
}
