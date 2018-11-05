package com.translatmaster.utils.image;

import android.graphics.drawable.Drawable;

/**
 * Created by lijian15 on 2016/12/19.
 */

public interface IImageLoadListener {
//    void onLoadingStarted(View view);
//    void onLoadingFailed(View view);
    void onLoadingComplete(Drawable drawable);
//    void onLoadingCancelled(View view);
}
