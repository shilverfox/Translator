package com.translatmaster.view.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.adapter.UniversalAdapter2;
import com.translatmaster.adapter.UniversalViewHolder2;
import com.translatmaster.utils.Router;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.utils.image.IImageLoadListener;
import com.translatmaster.utils.image.ImageLoader;
import com.translatmaster.view.main.entity.Album;
import com.translatmaster.view.main.entity.SpecialAlbums;
import com.translatmaster.view.myinfo.entity.MyInfoItem;

/**
 * 视频项，首页推荐，查询结果之类的
 */
public class VideoItemAdapter extends UniversalAdapter2<SpecialAlbums> {
    private OnMyItemClickListener onMyItemClickListener;

    public VideoItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(final UniversalViewHolder2 holder, final SpecialAlbums specialAlbum, final int position) {
        if (specialAlbum == null) {
            return;
        }

        Album album = specialAlbum.getAlbum();

        if (album == null) {
            return;
        }

        // 数量
        holder.setText(R.id.iv_video_item_amount, album.getSongs() + "");

        // 描述、标题
        holder.setText(R.id.iv_video_item_description, album.getTitle());

        // 主讲
        TextView tvHost = (TextView) holder.getViewById(R.id.iv_video_item_host);
        if (tvHost != null) {
            tvHost.setText(album.getTitle());
        }

        // 图片
        String imgUrl = album.getAppImageUrl();
        ImageLoader.displayImage(imgUrl, (ImageView)holder.getViewById(R.id.iv_video_item_image));

        holder.getViewById(R.id.layout_video_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMyItemClickListener != null) {
                    onMyItemClickListener.onClick(position);
                }
                ShowTools.toast("Video item clicked");
            }
        });
    }

    public interface OnMyItemClickListener {
        void onClick(int id);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }
}