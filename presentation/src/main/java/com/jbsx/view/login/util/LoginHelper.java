package com.jbsx.view.login.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.dialog.JDDJDialogFactory;
import com.jbsx.utils.EncodeTool;
import com.jbsx.utils.Router;
import com.jbsx.view.login.LoginActivity;
import com.jbsx.view.login.data.LoginData;

public class LoginHelper {
    public final static String LOGIN_FILE_NAME = "login";
    public final static String LOGIN_USER_INFO_KEY = "login_user_info";
    public final static String LOGIN_CODE_INFO_KEY = "login_code_info";

    private static LoginHelper sInstance;

    private LoginData mLoginData;

    public static LoginHelper getInstance() {
        if (sInstance == null) {
            sInstance = new LoginHelper();
        }

        return sInstance;
    }

    public  LoginData getLoginUser() {
        return mLoginData;
    }

    public String getUserToken() {
        String token = "";
        if (isLogin()) {
            token = mLoginData.getPayload().getToken();
        }

        return token;
    }

    public String getUserId() {
        String id = "";
        if (isLogin()) {
            id = mLoginData.getPayload().getId();
        }

        return id;
    }

    public  void setLoginUser(LoginData loginUser) {
        mLoginData = loginUser;
    }

    public void saveData() {
        SharedPreferences sharedPreferences = MainApplicationLike.getAppContext()
                .getSharedPreferences(LOGIN_FILE_NAME, Context.MODE_MULTI_PROCESS);
        Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        if (mLoginData != null) {
            String sting = gson.toJson(mLoginData);
            String newEncode = EncodeTool.encryptByNewAES(sting);
            editor.putInt(LOGIN_CODE_INFO_KEY, 1);
            editor.putString(LOGIN_USER_INFO_KEY, newEncode);
            editor.commit();
        }
    }

    public void readData() {
		SharedPreferences sharedPreferences = MainApplicationLike.getAppContext()
				.getSharedPreferences(LOGIN_FILE_NAME, Context.MODE_MULTI_PROCESS);
		String sting = sharedPreferences.getString(LOGIN_USER_INFO_KEY, "");
		int code = sharedPreferences.getInt(LOGIN_CODE_INFO_KEY, 0);
		if (code == 1) {
			sting = EncodeTool.decryptByNewAES(sting);
		} else {
			saveData();
		}

		if (!TextUtils.isEmpty(sting)) {
			Gson gson = new Gson();

			try {
				mLoginData = gson.fromJson(sting, LoginData.class);
			} catch (Exception e) {
				// 解密数据失败，清空登录态
				clearLoginInfo();
			}
		} else {
			clearLoginInfo();
		}
    }

    private void clearLoginInfo() {
        clearData();
        clearInternalMemory();
    }

    public void clearInternalMemory() {
        mLoginData = null;
    }

    public void clearData() {
        SharedPreferences sharedPreferences = MainApplicationLike.getAppContext()
                .getSharedPreferences(LOGIN_FILE_NAME, Context.MODE_MULTI_PROCESS);
        Editor editor = sharedPreferences.edit();
		editor.putString(LOGIN_USER_INFO_KEY, "");
        editor.commit();
    }

    public boolean isLogin() {
        return mLoginData != null && mLoginData.getPayload() != null;
    }

    public void logOut() {
        clearLoginInfo();
    }

    /**
     * 唤起登录页
     *
     * @param activity
     */
    public void startLogin(Activity activity) {
        if (activity != null) {
            Router.getInstance().open(LoginActivity.class, activity);
        }
    }

    // 显示登陆提示
    public void showLoginDialog(Context context) {
        if (context != null) {
            JDDJDialogFactory.createDialog(context).setTitle("请先登录")
                    .setFirstOnClickListener("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }
    }
}
