package com.jbsx.view.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.LocalPictureGalleryData;
import com.jbsx.view.main.entity.NavigationData;

import java.util.List;

/**
 * 本地图集相册
 */
public class LocalPictureGalleryAdapter extends RecyclerView.Adapter<LocalPictureGalleryAdapter.LocalPictureGalleryViewHolder> {
    private Context mContext;
    private List<LocalPictureGalleryData.PictureItem> mGalleryData;
    private OnGalleryItemClick mItemClickListener;

    public LocalPictureGalleryAdapter(Context c, OnGalleryItemClick itemClick) {
        mContext = c;
        mItemClickListener = itemClick;
    }

    public void setData(List<LocalPictureGalleryData.PictureItem> data) {
        mGalleryData = data;
    }

    @Override
    public LocalPictureGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_layout_item, parent, false);
        return new LocalPictureGalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LocalPictureGalleryViewHolder holder, final int position) {
        if (mGalleryData == null || mGalleryData.isEmpty()) {
            return;
        }

        final LocalPictureGalleryData.PictureItem entity = mGalleryData.get(position);
        holder.mTvLabel.setText(entity.getPictureDesc());
//        ImageLoader.displayImage(entity.getPictureFilePath(), holder.img);
        ImageLoader.showReverseImage(entity.getPictureFilePath(), null, holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(entity.getAtlasCode(), false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGalleryData == null ? 0 : mGalleryData.size();
    }

    class LocalPictureGalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView mTvLabel;
        public LocalPictureGalleryViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            mTvLabel = itemView.findViewById(R.id.tv_gallery_label);
        }
    }

    public interface OnGalleryItemClick {
        void onItemClick(String classifyCode, boolean isHasChildren);
    }
}
