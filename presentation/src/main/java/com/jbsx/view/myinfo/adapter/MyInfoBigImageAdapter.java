package com.jbsx.view.myinfo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jbsx.R;
import com.jbsx.adapter.UniversalAdapter2;
import com.jbsx.adapter.UniversalViewHolder2;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.myinfo.entity.MyInfoBigItem;
import com.jbsx.view.web.WebHelper;

/**
 * 我的适配器
 * 大图
 */
public class MyInfoBigImageAdapter extends UniversalAdapter2<MyInfoBigItem> {
    private OnMyItemClickListener onMyItemClickListener;

    public MyInfoBigImageAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(UniversalViewHolder2 holder, final MyInfoBigItem myInfoItem, final int position) {
        if (myInfoItem == null) {
            return;
        }

        // 标题
        holder.setText(R.id.tv_myinfo_item_title, myInfoItem.getTitle());

        // 图片
        String imgUrl = myInfoItem.getImgUrl();
        ImageView iv = (ImageView) holder.getViewById(R.id.iv_myinfo_item_icon);
        ImageLoader.displayImage(myInfoItem.getImgUrl(), iv);

        // 作者
        holder.setText(R.id.tv_myinfo_item_author, myInfoItem.getAuthor());

        // 简介
        holder.setText(R.id.tv_myinfo_item_summary, myInfoItem.getSummary());

        holder.getViewById(R.id.rl_myinfo_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入到相应界面
                if (onMyItemClickListener != null) {
                    onMyItemClickListener.onClick(position);
                }

                handleGoToView(myInfoItem);
            }
        });
    }

    private void handleGoToView(final MyInfoBigItem myInfoItem) {
        WebHelper.openWeb(mContext, myInfoItem.getToUrl(), myInfoItem.getTitle());
    }

    public interface OnMyItemClickListener {
        void onClick(int id);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }
}
