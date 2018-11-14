

package com.jbsx.customview.dialog;

import android.view.View;

/**
 * 对话框入参
 */
public final class DialogParams {
    float percentageOfScreenHeight=0.8f;
    float percentageOfScreenWidth=0.78f;
    String title;
    String msg;
    CharSequence msg2;
    View view;
    boolean isCancel = true;
    int btnCount;
    int animId=-1;
    DialogBtnParams firstParams;
    DialogBtnParams secondParams;
    DialogBtnParams thirdParams;

    public void clear() {
        percentageOfScreenHeight=0.8f;
        percentageOfScreenWidth=0.78f;
        title = "";
        msg = "";
        view = null;
        isCancel = true;
        btnCount=0;
        animId=-1;
        if (firstParams != null) {
            firstParams=null;
        }
        if (secondParams != null) {
            secondParams=null;
        }
        if (thirdParams != null) {
            thirdParams=null;
        }
    }
}
