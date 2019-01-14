package com.jbsx.view.main.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.SpecialSingles;
import com.jbsx.view.search.view.SearchResultHolder;

public class RepertoryListHolder extends SearchResultHolder {
    public RepertoryListHolder(Context context, View view) {
        super(context, view);
    }

    @Override
    public void drawViews(SpecialSingles data, int position) {
        mData = data;
        mCurrentPosition = position;

        if (data != null && data.getSingle() != null) {
            Single single = data.getSingle();

            // 单一一级不显示数量
            mTvAmount.setVisibility(View.INVISIBLE);

            // 描述、标题
            mTvTitle.setText(single.getTitle());

            // 主讲
            if (mTvCelebrity != null) {
                mTvCelebrity.setText(getCelebrity());
            }

            // 图片
            String imgUrl = single.getAppImageUrl();
            ImageLoader.displayImage(imgUrl, mIvImageUrl);
        }
    }
}
