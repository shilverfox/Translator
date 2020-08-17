package com.jbsx.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by zhangfaming on 2017/12/8.
 */

public class KeyBoardUtil {

    public static void showSoftInput(final EditText editText){
        if (editText != null) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.setFocusable(true);
                    editText.requestFocus();
                    InputMethodManager inputManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                }
            }, 200);
        }
    }

    public static void hideSoftInput(EditText editText){
        if (editText != null) {
            editText.setText("");
            editText.clearFocus();
            InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
    public static void hideSoftInput(EditText editText, boolean isClearText){
        if (editText != null) {
            if (isClearText) {
                editText.setText("");
            }
            editText.clearFocus();
            InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
}
