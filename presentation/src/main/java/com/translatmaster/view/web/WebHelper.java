package com.translatmaster.view.web;

import android.content.Context;
import android.os.Bundle;

import com.translatmaster.utils.Router;

public class WebHelper {
    public final static String WEB_URL_KEY = "web_url_key";
    public final static String WEB_TITLE_KEY = "web_title_key";

    public static void openWeb(Context context, String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(WEB_URL_KEY, url);
        bundle.putString(WEB_TITLE_KEY, title);
        Router.getInstance().open(WebActivity.class, context, bundle);
    }
}
