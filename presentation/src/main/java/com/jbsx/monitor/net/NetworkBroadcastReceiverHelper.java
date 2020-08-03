package com.jbsx.monitor.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

/**
 * 网络状态广播接受者帮助类
 */
public class NetworkBroadcastReceiverHelper {
    private Context mContext;
    private BroadcastReceiver mReceiver;
    private OnNetworkStateChangedListener mOnNetworkStateChangedListener;
    public static final String ACTION_NO_CONNECTION = "ACTION_NO_CONNECTION"; //网络没有连接
    public static final String ACTION_CONNECTIONED = "ACTION_CONNECTIONED";   //网络连接
    public NetworkBroadcastReceiverHelper(Context context, OnNetworkStateChangedListener listener) {
        this.mContext = context;
        this.mOnNetworkStateChangedListener = listener;
    }

    public void register() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (TextUtils.equals(ACTION_CONNECTIONED, intent.getAction())) {
                    if (mOnNetworkStateChangedListener != null) {
                        mOnNetworkStateChangedListener.onConnected();
                    }
                } else if (TextUtils.equals(ACTION_NO_CONNECTION, intent.getAction())) {
                    if (mOnNetworkStateChangedListener != null) {
                        mOnNetworkStateChangedListener.onDisConnected();
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CONNECTIONED);
        filter.addAction(ACTION_NO_CONNECTION);
        LocalBroadcastUtils.register(mContext, mReceiver, filter);
    }

    public void setOnNetworkStateChangedListener(OnNetworkStateChangedListener onNetworkStateChangedListener) {
        this.mOnNetworkStateChangedListener = onNetworkStateChangedListener;
    }

    public OnNetworkStateChangedListener getOnNetworkStateChangedListener() {
        return this.mOnNetworkStateChangedListener;
    }

    public void unregister() {
        LocalBroadcastUtils.unregister(mContext, mReceiver);
    }

    public interface OnNetworkStateChangedListener {
        void onConnected();
        void onDisConnected();
    }
}
