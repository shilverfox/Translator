package com.translatmaster.utils.image;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.translatmaster.app.MainApplication;

/**
 * Handle showing and loading of images.
 *
 * Created by lijian15 on 2016/12/19.
 */

public class ImageLoader {
    /**
     * Load a image and set it to the imageView
     *
     * @param url
     * @param imgView
     */
    public static void displayImage(String url, ImageView imgView) {
        if (TextUtils.isEmpty(url) || imgView == null) {
            return;
        }

        Glide.with(MainApplication.getAppContext())
                .load(url)
                .into(imgView);
    }

    /**
     * Load a image and call the callback method.
     *
     * @param url
     * @param listener
     */
    public static void loadImage(String url, final IImageLoadListener listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Glide.with(MainApplication.getAppContext())
                .load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (listener != null) {
                            listener.onLoadingComplete(resource);
                        }
                    }
                });
    }
}
