package com.jbsx.view.myinfo.view.video;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.player.util.PlayerHelper;
import com.jbsx.player.util.SingleVideoUtil;
import com.jbsx.utils.DateUtil;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.utils.DataUtil;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.UserSingle;
import com.jbsx.view.main.entity.ViewHistoryData;
import com.jbsx.view.myinfo.fragment.MyViewHistoryFragment;
import com.jbsx.view.myinfo.util.SortListUtil;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class MyInfoVideoViewHolder extends CommonListFragmentViewHolder<UserSingle> {
    private Context mContext;

    private View mRootView;
    private TextView mTvGroup;
    private TextView mTvAmount;
    private TextView mTvTitle;
    private TextView mTvCelebrity;
    private ImageView mIvImageUrl;
    private ImageView mIvCheck;

    private UserSingle mData;
    private int mCurrentPosition;

    private CommonListFragmentAdapter mAdapter;

    private OnMyItemClickListener onMyItemClickListener;
    private OnSelectIconClickListener mOnSelectIconClickListener;

    public MyInfoVideoViewHolder(Context context, View view) {
        super(view);
        mContext = context;
    }

    @Override
    public void findViews(View rootView) {
        if (rootView != null) {
            mRootView = rootView;

            mTvGroup  = mRootView.findViewById(R.id.iv_my_info_video_group);
            mTvAmount = mRootView.findViewById(R.id.iv_video_item_amount);
            mTvTitle = mRootView.findViewById(R.id.iv_video_item_description);
            mTvCelebrity = mRootView.findViewById(R.id.iv_video_item_host);
            mIvImageUrl = mRootView.findViewById(R.id.iv_video_item_image);
            mIvCheck = mRootView.findViewById(R.id.iv_my_info_video_check);
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
    public void drawViews(UserSingle data, final int position) {
        mData = data;
        mCurrentPosition = position;

        if (data != null) {
            // 分组，只显示今天明天和更早
            String group = data.getCreatedAt();
            boolean hasGroupText = SortListUtil.groupIsValid(group);
            mTvGroup.setVisibility(hasGroupText ? View.VISIBLE : View.GONE);
            mTvGroup.setText(hasGroupText ? group : "");
        }

        if (data != null && data.getSingle() != null) {
            final Single single = data.getSingle();

            // 单一一级不显示数量
            mTvAmount.setVisibility(View.INVISIBLE);

            // 描述、标题
            mTvTitle.setText(single.getTitle());

            // 时长
            if (mTvCelebrity != null) {
                mTvCelebrity.setTextColor(0xff989898);
                mTvCelebrity.setText(getViewTime());
            }

            // 选中
            mIvCheck.setVisibility(MyViewHistoryFragment.mCanSelectItem ? View.VISIBLE : View.GONE);
            boolean isCheck = single.isCheck();
            mIvCheck.setImageResource(isCheck ? R.drawable.red_rect_check : R.drawable.rect_uncheck);

            mIvCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnSelectIconClickListener != null) {
                        mOnSelectIconClickListener.onClick(position);
                    }

                    if (mAdapter != null) {
                        mAdapter.notifyItemChanged(position);
                    }

                    single.setCheck(!single.isCheck());
                }
            });

            // 图片
            String imgUrl = single.getAppImageUrl();
            ImageLoader.displayImage(imgUrl, mIvImageUrl);
        }
    }

    public void setAdapter(CommonListFragmentAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * 观看时长
     *
     * @return
     */
    private String getViewTime() {
        String percent = "";

        if (mData != null && mData.getSingle() != null) {
            Single single = mData.getSingle();

            long currentPlay = Long.parseLong(mData.getSecond());
            long totalTime = DateUtil.parseMillSecond(single.getLongTime());

            percent = SingleVideoUtil.getWatchPercent(currentPlay, totalTime);
        }
        return "观看至：" + percent + "%";
    }

    @Override
    public void isTheLastLine(boolean theLastLine) {
    }

    private void handleRootViewClick(int position) {
        if (onMyItemClickListener != null) {
            onMyItemClickListener.onClick(position);
        }

        PlayerHelper.gotoPlayer((Activity)mContext, PlayerHelper.makePlayerData(
                mData.getSingle().getSpecialAlbumId(),
                mData.getSingle().getId(), ConstData.VIDEO_DEFINITION_TYPE_STAND));
    }

    public interface OnMyItemClickListener {
        void onClick(int position);
    }

    public interface OnSelectIconClickListener {
        void onClick(int position);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }

    public void setOnSelectIconClickListener(OnSelectIconClickListener onMyItemClickListener) {
        mOnSelectIconClickListener = onMyItemClickListener;
    }
}
