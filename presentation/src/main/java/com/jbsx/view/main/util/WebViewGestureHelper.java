package com.jbsx.view.main.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.customview.IconTextView;
import com.jbsx.utils.LogTools;
import com.jbsx.utils.StatisticsReportUtil;

import java.text.DecimalFormat;

/**
 * webView视频的手势控制（音量、亮度）
 *
 * https://blog.csdn.net/wuditwj/article/details/83021884
 */
public class WebViewGestureHelper {
    // 点击纵坐标
    private float dY = 0;
    // 点击横坐标
    private float dX = 0;
    // 抬起纵坐标
    private float uY = 0;
    // 抬起横坐标
    private float uX = 0;

    // 用来记录坐标值
    private float uY1;

    // 屏幕当前亮度百分比
    private float mLightPercent = 0;

    /** 系统最大音量 */
    private int mMaxSound;

    //媒体音量管理
    private AudioManager mAudioManager;

    private View mControllerView;
    private IconTextView mIconView;
    private TextView mTvIconText;

    private Context mContext;

    public WebViewGestureHelper(Context context, View controllerView) {
        mContext = context;
        mControllerView = controllerView;
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxSound = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        findViews();
    }

    private void findViews() {
        if (mControllerView != null) {
            mIconView = mControllerView.findViewById(R.id.icon_view);
            mTvIconText = mControllerView.findViewById(R.id.icon_text);
        }
    }

    public void handleGesture(View videoView) {
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    // 按下
                    case MotionEvent.ACTION_DOWN:
                        dX = motionEvent.getX();
                        dY = motionEvent.getY();
                        uY1 = dY;
                        if (dX > StatisticsReportUtil.getScreenWidth() / 2) {//声音控制
                            // 获取当前音量
                            double currentSount = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                            double i = currentSount / mMaxSound;
                            if (i == 0) {
                                mIconView.setText(R.string.ic_volume_no);
                            } else {
                                mIconView.setText(R.string.ic_volume);
                            }
                            // 设置百分比
                            mTvIconText.setText(doubleToString(i) + "");
                        } else if (dX <= StatisticsReportUtil.getScreenWidth() / 2) {//亮度控制
                            mIconView.setText(R.string.ic_sun);
                            // 设置百分比
                            mTvIconText.setText(doubleToString(mLightPercent));
                        }
                        break;
                    // 抬起
                    case MotionEvent.ACTION_UP:
                        mControllerView.setVisibility(View.GONE);
                        break;
                    // 移动
                    case MotionEvent.ACTION_MOVE:
                        uY = motionEvent.getY();
                        uX = motionEvent.getX();
                        if (uY == uY1) {
                            LogTools.e("--==", "滑动停止");
                        } else {
                            LogTools.e("--==", "正在滑动");
                            if (dX > StatisticsReportUtil.getScreenWidth() / 2) {//声音控制
                                if (Math.abs(uY1 - uY) > 3)
                                    setVolume(uY1 - uY);
                            } else if (dX <= StatisticsReportUtil.getScreenWidth() / 2) {//亮度控制
                                if (Math.abs(uY1 - uY) > 1)
                                    setLight(uY1 - uY);
                            }
                            uY1 = uY;
                        }

                        break;
                }
                return false;
            }
        });
    }

    /**
     * double转String,保留小数点后两位
     *
     * @param num
     * @return
     */
    public static String doubleToString(double num) {
        double v = num * 100;
        // 使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0").format(v);
    }

    //手势调节音量
    private void setVolume(float vol) {
        mControllerView.setVisibility(View.VISIBLE);
        if (vol > 0) {//增大音量
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
        } else if (vol < 0) {//降低音量
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
        } else if (vol == 0) {

        }

        // 获取当前音量
        double currentSount = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        double i = currentSount / mMaxSound;
        if (i == 0) {
            mIconView.setText(R.string.ic_volume_no);
        } else {
            mIconView.setText(R.string.ic_volume);
        }
        // 设置百分比
        mTvIconText.setText(doubleToString(i) + "");
    }

    /**
     * 手势设置屏幕亮度
     * 设置当前的屏幕亮度值，及时生效 0.004-1
     * 该方法仅对当前应用屏幕亮度生效
     */
    private void setLight(float vol) {
        mControllerView.setVisibility(View.VISIBLE);
        Window localWindow = ((Activity)mContext).getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        mLightPercent += (vol / StatisticsReportUtil.getScreenWidth()) * 4;
        if (mLightPercent > 1) {
            mLightPercent = 1f;
        } else if (mLightPercent <= 0) {
            mLightPercent = 0.000f;
        }
        localLayoutParams.screenBrightness = mLightPercent;
        localWindow.setAttributes(localLayoutParams);
        mIconView.setText(R.string.ic_sun);
        //设置百分比
        mIconView.setText(doubleToString(mLightPercent));
    }


}
