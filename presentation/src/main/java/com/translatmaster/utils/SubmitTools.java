package com.translatmaster.utils;

public class SubmitTools {
    private static final int TIME_INTERVAL = 1000;
    private static long mLastClickTime;
    private static long mCurClickTime;

    public SubmitTools() {
    }

    public static boolean canSubmit() {
        mCurClickTime = System.currentTimeMillis();
        if (mLastClickTime != 0L && mCurClickTime - mLastClickTime < 1000L) {
            mLastClickTime = mCurClickTime;
            return false;
        } else {
            mLastClickTime = mCurClickTime;
            return true;
        }
    }
}
