package com.jbsx.utils;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;

public class ErroBarHelper {

    public static int ERRO_TYPE_NET = 0;
    public static int ERRO_PARENT_ID = -10001;

    public static String ERRO_TYPE_NET_INTERNET = "网络开小差，请稍后再试哦~";
    public static String ERRO_TYPE_NET_NAME = "网络开小差，请稍后再试哦~";
    public static String ERRO_TYPE_GPS_NAME = "无法获取您的位置信息";
    public static String ERRO_TYPE_PARSE_NAME = "服务端繁忙，请稍候再试";
    public static String ERRO_TYPE_NO_DATA = "没有找到符合的商品";
    public static String ERRO_TYPE_GPS_NAME_NEW = "无法获取地址";

    private static View creatErroBar() {
        View LoadingErroBar = LayoutInflater.from(MainApplicationLike.getAppContext())
                .inflate(R.layout.erro_bar2, null);
        return LoadingErroBar;
    }

    public static void addErroBar(View content, String title) {
        addErroBar(content, title, -1, null, "");
    }

    public static void addErroBar(View content, String title, final Runnable firstBtnClick) {
        addErroBar(content, title, -1, firstBtnClick, "重新加载");
    }

    public static void addErroBar(View content, String title, final Runnable firstBtnClick
            , String firstBtnName) {
        addErroBar(content, title, -1, firstBtnClick, firstBtnName);
    }


    public static void addErroBar(final View content, final String title, final int imgResId,
                                  final Runnable firstBtnClick, final String firstBtnName) {
        addErroBar(content, title, imgResId, firstBtnClick, firstBtnName, null, null);
    }


    public static void addErroBar(final View content, final String title, final int imgResId,
                                  final Runnable firstBtnClick, final String firstBtnName, final Runnable secondBtnClick, final String secondBtnName) {
        addErroBar(content, title, imgResId, firstBtnClick, firstBtnName, secondBtnClick, secondBtnName, null);
    }

    public static void addErroBar(final View content, final String title, final int imgResId,
                                  final Runnable firstBtnClick, final String firstBtnName, final String subTitle) {
        addErroBar(content, title, imgResId, firstBtnClick, firstBtnName, null, null, subTitle);
    }

    public static void addErroBar(final View content, final String title, final int imgResId,
                                  final Runnable firstBtnClick, final String firstBtnName
            , final Runnable secondBtnClick, final String secondBtnName, final String subTitle) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addErroBarInUiThread(content, title, imgResId, firstBtnClick, firstBtnName, secondBtnClick, secondBtnName, subTitle);
            }
        };
        MainApplicationLike.getInstance().getHanlder().post(runnable);
    }

    private static void initErroBar(View LoadingErroBar, String title, int imgResId, final Runnable firstBtnClick,
                                    String firstBtnName, final Runnable secondBtnClick, String secondBtnName, String subTitle) {
        if (imgResId >= 0) {
            ImageView imageView = (ImageView) LoadingErroBar.findViewById(R.id.erroIcon);
            imageView.setImageResource(imgResId);
        }
        TextView tvTitle = (TextView) LoadingErroBar.findViewById(R.id.title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.INVISIBLE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        Button btnFirst = (Button) LoadingErroBar.findViewById(R.id.refresh);
        if (firstBtnClick != null && !TextUtils.isEmpty(firstBtnName)) {
            btnFirst.setText(firstBtnName);
            btnFirst.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    firstBtnClick.run();
                }
            });
            btnFirst.setVisibility(View.VISIBLE);
        } else {
            btnFirst.setVisibility(View.GONE);
        }

        Button btnSecond = (Button) LoadingErroBar.findViewById(R.id.location);
        if (secondBtnClick != null && !TextUtils.isEmpty(secondBtnName)) {
            btnSecond.setText(secondBtnName);
            btnSecond.setVisibility(View.VISIBLE);
            btnSecond.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    secondBtnClick.run();
                }
            });
        } else {
            btnSecond.setVisibility(View.GONE);
        }

        TextView tvSubTitle = (TextView) LoadingErroBar.findViewById(R.id.sub_title);
        if (!TextUtils.isEmpty(subTitle)) {
            tvSubTitle.setText(subTitle);
            tvSubTitle.setVisibility(View.VISIBLE);
        } else {
            tvSubTitle.setVisibility(View.GONE);
        }
    }

    private static void addErroBarInUiThread(View content, String title, int imgResId, final Runnable firstBtnClick,
                                             String firstBtnName, Runnable secondBtnClick, String secondBtnName, String subTitle) {
        if (content == null) {
            return;
        }
        BarHelper.cleanAllBar(content);
        ViewGroup contentParent = (ViewGroup) content.getParent();
        if (contentParent == null) {
            return;
        }
        int index = contentParent.indexOfChild(content);
        contentParent.removeView(content);
        RelativeLayout layout = new RelativeLayout(content.getContext());// 定义框架布局器
        layout.setId(ERRO_PARENT_ID);
        contentParent.addView(layout, index,
                content.getLayoutParams());
        layout.addView(content, content.getLayoutParams());// 添加组件
        content.setVisibility(View.GONE);
        View LoadingErroBar = creatErroBar();
        initErroBar(LoadingErroBar, title, imgResId, firstBtnClick, firstBtnName, secondBtnClick, secondBtnName, subTitle);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layout.addView(LoadingErroBar, params);
    }


    public static void removeErroBar(View content) {
        if (content == null) return;
        ViewGroup contentParent = (ViewGroup) content.getParent();
        if (contentParent != null && contentParent.getId() == ERRO_PARENT_ID) {
            contentParent.removeView(content);
            ViewGroup contentParentParent = (ViewGroup) contentParent.getParent();
            if (contentParentParent != null) {
                int index = contentParentParent.indexOfChild(contentParent);
                contentParentParent.removeView(contentParent);
                contentParentParent.addView(content, index,
                        contentParent.getLayoutParams());
                content.setVisibility(View.VISIBLE);
            }
        }
    }
}
