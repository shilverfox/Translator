package com.translatmaster.view.login.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.utils.EncodeTool;
import com.translatmaster.view.login.data.LoginData;

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
    }

    public void clearData() {
        SharedPreferences sharedPreferences = MainApplicationLike.getAppContext()
                .getSharedPreferences("login", Context.MODE_MULTI_PROCESS); // 私有数据
        Editor editor = sharedPreferences.edit();// 获取编辑器
//		editor.putString(NEW_AES_KEY_NAME, "");
        editor.commit();// 提交修改
    }
}
