package com.jbsx.view.search.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.player.util.PlayerHelper;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.utils.DataUtil;
import com.jbsx.view.main.entity.RepertoryData;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.SpecialSingles;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class SearchResultHolder extends CommonListFragmentViewHolder<SpecialSingles> {
    private Context mContext;

    private View mRootView;
    private TextView mTvAmount;
    private TextView mTvTitle;
    private TextView mTvCelebrity;
    private TextView mTvGuwen;
    private ImageView mIvImageUrl;

    private SpecialSingles mData;
    private int mCurrentPosition;

    /** 是否显示艺术顾问 */
    private boolean mShowGuwen;

    public SearchResultHolder(Context context, View view) {
        super(view);
        mContext = context;
    }

    public void showGuwen(boolean show) {
        mShowGuwen = show;
    }

    @Override
    public void findViews(View rootView) {
        if (rootView != null) {
            mRootView = rootView;

            mTvAmount = mRootView.findViewById(R.id.iv_video_item_amount);
            mTvTitle = mRootView.findViewById(R.id.iv_video_item_description);
            mTvCelebrity = mRootView.findViewById(R.id.iv_video_item_host);
            mTvGuwen = mRootView.findViewById(R.id.iv_video_item_guwen);
            mIvImageUrl = mRootView.findViewById(R.id.iv_video_item_image);
        }
    }

    @Override
    public void registerEvent() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRootViewClick(mCurrentPosition);
            }
        });
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

            // 顾问
            if (mTvGuwen != null) {
                String guwen = getGuwen();
                mTvGuwen.setVisibility((mShowGuwen && !TextUtils.isEmpty(guwen))
                        ? View.VISIBLE : View.GONE);
                mTvGuwen.setText(guwen);
            }

            // 图片
            String imgUrl = single.getAppImageUrl();
            ImageLoader.displayImage(imgUrl, mIvImageUrl);
        }
    }

    /**
     * 主讲人
     *
     * @return
     */
    private String getCelebrity() {
        String name = "";

        if (mData != null) {
            name = DataUtil.getCelebrity(mData.getCelebrities());
        }
        return "主讲：" + name;
    }

    private String getGuwen() {
        String name = "";

        if (mData != null) {
            name = DataUtil.getGuwen(mData.getCelebrities());
        }
        return "艺术顾问：" + name;
    }

    @Override
    public void isTheLastLine(boolean theLastLine) {
    }

    private void handleRootViewClick(int position) {
        if (mClickListener != null) {
            mClickListener.onClick(position);
        }

        PlayerHelper.gotoPlayer((Activity)mContext, PlayerHelper.makePlayerData(
                mData.getSingle().getSpecialAlbumId(),
                mData.getSingle().getId(), ConstData.VIDEO_DEFINITION_TYPE_STAND));
    }
}
