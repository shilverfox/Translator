package com.jbsx.utils.image;

import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.ColorTools;
import com.jbsx.utils.UiTools;

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

        Glide.with(MainApplicationLike.getAppContext())
                .load(url)
                .into(imgView);
    }

    public static void displayImage(String url, int defaultImageId, ImageView imgView) {
        if (imgView == null) {
            return;
        }

        Glide.with(MainApplicationLike.getAppContext())
                .load(url)
                .placeholder(defaultImageId)
                .into(imgView);
    }

    /**
     * 圆角图
     *
     * @param url
     * @param imgView
     * @param roundRadius
     */
    public static void displayImage(String url, ImageView imgView, int roundRadius) {
        if (TextUtils.isEmpty(url) || imgView == null) {
            return;
        }

        Glide.with(MainApplicationLike.getAppContext())
                .load(url)
                .asBitmap()
                .transform(new Transformation[]{makeCornerTransform(roundRadius)})
                .into(imgView);
    }

    private static CornerTransform makeCornerTransform(int roundRadius) {
        CornerTransform transformation1 = new CornerTransform(MainApplicationLike.getAppContext(),
                (float) UiTools.dip2px(roundRadius));
        transformation1.setExceptCorner(false, false, false, false);
        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setCornerRadius(UiTools.dip2px(6));
        drawable1.setColor(ColorTools.parseColor("#f4f4f4"));

        return transformation1;
    }

    private static GlideCircleTransform makeCircleTransform() {
        GlideCircleTransform transformation1 = new GlideCircleTransform(MainApplicationLike.getAppContext());
        return transformation1;
    }

    /**
     * 圆形图
     *
     * @param url
     * @param imgView
     * @param circle
     */
    public static void displayImage(String url, ImageView imgView, int defaultImageId, boolean circle) {
        if (imgView == null) {
            return;
        }

        Glide.with(MainApplicationLike.getAppContext())
                .load(url)
                .asBitmap()
                .placeholder(defaultImageId)
                .transform(new Transformation[]{makeCircleTransform()})
                .into(imgView);
    }

    public static void displayImage(Uri uri, int defaultImageId, ImageView imgView, boolean circle) {
        if (imgView == null) {
            return;
        }

        Glide.with(MainApplicationLike.getAppContext())
                .load(uri)
                .asBitmap()
                .placeholder(defaultImageId)
                .transform(new Transformation[]{makeCircleTransform()})
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

        Glide.with(MainApplicationLike.getAppContext())
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
