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
import com.jbsx.view.main.entity.NavigationData;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context mContext;
    private List<NavigationData.ClassifyEntity> mGalleryData;
    private OnGalleryItemClick mItemClickListener;

    public GalleryAdapter(Context c, OnGalleryItemClick itemClick) {
        mContext = c;
        mItemClickListener = itemClick;
    }

    public void setData(List<NavigationData.ClassifyEntity> data) {
        mGalleryData = data;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_layout_item, parent, false);
        return new GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GalleryViewHolder holder, final int position) {
        if (mGalleryData == null || mGalleryData.isEmpty()) {
            return;
        }

        final NavigationData.ClassifyEntity entity = mGalleryData.get(position);
        holder.mTvLabel.setText(entity.getClassifyName());
        ImageLoader.displayImage(entity.getClassifyPreview(), holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(entity.getClassifyCode(), entity.isHasChildren());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGalleryData == null ? 0 : mGalleryData.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView mTvLabel;
        public GalleryViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            mTvLabel = itemView.findViewById(R.id.tv_gallery_label);
        }
    }

    public interface OnGalleryItemClick {
        void onItemClick(String classifyCode, boolean isHasChildren);
    }
}
