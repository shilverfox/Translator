package com.jbsx.view.search.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.jbsx.R;
import com.jbsx.customview.RadiusBackgroundSpan;
import com.jbsx.customview.TextTagSpan;
import com.jbsx.customview.Truss;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.data.AppConstData;
import com.jbsx.player.util.AlbumDetailUtil;
import com.jbsx.player.util.PlayerHelper;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.utils.DataUtil;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.SpecialSingles;
import com.jbsx.view.main.entity.Tag;

import java.util.List;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class SearchResultHolder extends CommonListFragmentViewHolder<SpecialSingles> {
    private final static String MAO = "：";

    private Context mContext;

    protected View mRootView;
    protected TextView mTvAmount;
    protected TextView mTvTitle;
    protected TextView mTvCelebrity;
    protected TextView mTvGuwen;
    protected ImageView mIvImageUrl;

    protected SpecialSingles mData;
    protected int mCurrentPosition;

    /** 是否显示艺术顾问 */
    protected boolean mShowGuwen;

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
            setTitle(mTvTitle, single.getTitle(), single.getType());

//            // 主讲
//            if (mTvCelebrity != null) {
//                mTvCelebrity.setText(getCelebrity());
//            }
//
//            // 顾问
//            if (mTvGuwen != null) {
//                String guwen = getGuwen();
//                mTvGuwen.setVisibility((mShowGuwen && !TextUtils.isEmpty(guwen))
//                        ? View.VISIBLE : View.GONE);
//                mTvGuwen.setText(guwen);
//            }

            mTvCelebrity.setText(getCelebrity());
            changeSomeColor(mTvCelebrity);

            if (mTvGuwen != null) {
                String firstTag = getFirstTag();
                mTvGuwen.setVisibility((mShowGuwen && !TextUtils.isEmpty(firstTag))
                        ? View.VISIBLE : View.GONE);
                mTvGuwen.setText(firstTag);
                changeSomeColor(mTvGuwen);
            }

            // 图片
            String imgUrl = single.getAppImageUrl();
            ImageLoader.displayImage(imgUrl, mIvImageUrl);
        }
    }

    public void changeSomeColor(TextView textView) {
        String label = null;
        if (textView.getText() != null) {
            label = textView.getText().toString();
        }

        if (label != null) {
            int index = label.indexOf(MAO);

            if (index != -1) {
                SpannableString spannableString = new SpannableString(label);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),
                        0, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }
        }
    }

    private String getFirstCelebrity() {
        StringBuffer nb = new StringBuffer();

        if (mData != null) {
            List<Celebrities> celebrities = mData.getCelebrities();
            if (celebrities != null && celebrities.size() > 0) {
                Celebrities hiMan = celebrities.get(0);
                if (hiMan != null) {
                    nb.append(AlbumDetailUtil.getTypeName(hiMan.getType()));
                    nb.append("：");
                    nb.append(hiMan.getName());
                }
            }
        }

        return nb.toString();
    }

    /**
     * 主讲人
     *
     * @return
     */
    protected String getCelebrity() {
        String name = "";

        if (mData != null) {
            name = DataUtil.getCelebrity(mData.getCelebrities());
        }

        if (TextUtils.isEmpty(name)) {
            return "";
        } else {
            return AlbumDetailUtil.getTypeName(AppConstData.CELEBRITY_TYPE_ZHUJIANG) + MAO + name;
        }
    }

    private String getTags() {
        StringBuffer nb = new StringBuffer();

        if (mData != null) {
            List<Tag> tags = mData.getTags();
            if (tags != null) {
                for(Tag tag : tags) {
                    nb.append(tag.getName());
                }
            }
        }
        return nb.toString();
    }

    private String getFirstTag() {
        StringBuffer nb = new StringBuffer();
        if (mData != null) {
            List<Tag> tags = mData.getTags();
            if (tags != null && !tags.isEmpty()) {
                Tag tag = tags.get(0);

                nb.append(AlbumDetailUtil.getTypeName(tag.getType()));
                nb.append(MAO);
                nb.append(tag.getName());
            }
        }
        return nb.toString();
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

    private String getLabelByType(int type) {
        String result = "";
        if (type == 1) {
            result = "片库";
        } else if (type == 2) {
            result = "专题";
        }

        return result;
    }

    /**
     * title后增加tag
     *
     * @param textView
     * @param receiptTitle
     * @param receiptType
     */
    private void setTitle(TextView textView, String receiptTitle, int receiptType) {
        if (textView != null && !TextUtils.isEmpty(receiptTitle)) {
            String tagContent = getLabelByType(receiptType);

            Truss truss = new Truss();
            TextTagSpan span = new TextTagSpan(mContext, UiTools.dip2px(25), UiTools.dip2px(15))
                    .setLeftMargin(UiTools.dip2px(5))
                    .setTextColor(Color.RED)
                    .setTextSize(UiTools.sp2px(10))
                    .setRadius(UiTools.dip2px(3))
                    .setStrokeWidth(UiTools.dip2px(0.5f))
                    .setStrokeColor(Color.RED);

            truss.append(receiptTitle)
                    .pushSpan(span)
                    .append(tagContent);
            textView.setText(truss.build());
        }
    }
}
