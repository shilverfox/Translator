package com.jbsx.utils.image;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by lijian15 on 2016/12/19.
 */

public interface IImageLoadListener {
//    void onLoadingStarted(View view);
    void onLoadingFailed(Drawable errorDrawable);
    void onLoadingComplete(Drawable drawable);
//    void onLoadingCancelled(View view);
}
