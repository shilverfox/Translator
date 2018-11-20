package com.jbsx.view.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.adapter.UniversalAdapter2;
import com.jbsx.adapter.UniversalViewHolder2;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.Album;
import com.jbsx.view.main.entity.SpecialAlbums;

/**
 * 视频项，首页推荐
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
        holder.setText(R.id.iv_video_item_amount, "全" + album.getSongs() + "集");

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
