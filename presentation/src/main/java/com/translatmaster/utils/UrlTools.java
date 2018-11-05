package com.translatmaster.utils;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlTools {
    public static final String SHARE = "share";
    public static final String NEED_PIN = "needPin";
    public static final String NEED_LOGIN = "needLogin";
    public static final String SHARE_URL = "shareUrl";
    public static final String DJ_SCHEME = "openApp.jddj:";
    public static final String BODY = "body";
    public static final String TOKEN = "token";

    public static String getValue(String urls, String key) {
        if (TextUtils.isEmpty(urls)) {
            return "";
        }
        urls = urls.split("#")[0];//去掉#后面的文字
        int index = urls.indexOf("?");
        String[] sr1 = null;
        if (index > 0 && index < urls.length() - 1) {
            String str1 = urls.substring(index + 1);
            sr1 = str1.split("&");
        }

        if (sr1 == null || sr1.length == 0) {
            return "";
        }

        for (String str : sr1) {
            int index1 = str.indexOf('=');
            if (index1 >= 1) {
                String key1 = str.substring(0, index1);
                String value = "";
                if (key1.equals(key) && index1 + 1 < str.length()) {
                    value = str.substring(index1 + 1);
                    try {
                        return URLDecoder.decode(value, "utf-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
//		    	 String[] tradeNum = str.split("=");
//	            if(tradeNum!=null&&tradeNum.length==2){
//	            	if(tradeNum[0].equals(key)){
//	            		try {
//							return URLDecoder.decode(tradeNum[1],"utf-8");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//	            	}
//	            }
        }
        return "";
    }

    public static String getToken(String url) {
        String token = "";
        try {
            String bodyStr = getValue(url, BODY);
            LogTools.e("getToken", "bodyStr=== " + bodyStr);
            JSONObject obj = new JSONObject(bodyStr);
            if (obj.has("params")) {
                String params = obj.getString("params");
                JSONObject objParams = new JSONObject(params);
                if (objParams.has(TOKEN)) {
                    token = objParams.getString(TOKEN);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static String getValue1(String urls, String key) {
        try {
            urls = URLDecoder.decode(urls, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(urls)) {
            return "";
        }
        urls = urls.split("#")[0];//去掉#后面的文字
        int index = urls.indexOf("?");
        String[] sr1 = null;
        if (index > 0 && index < urls.length() - 1) {
            String str1 = urls.substring(index + 1);
            sr1 = str1.split(",");
        }

        if (sr1 == null || sr1.length == 0) {
            return "";
        }

        for (String str : sr1) {
            int index1 = str.indexOf(':');
            if (index1 >= 1) {
                String key1 = str.substring(1, index1 - 1);
                String value = "";
                if (key1.equals(key)) {
                    value = str.substring(index1 + 2, str.length() - 2);
                    return value;
                }
            }
        }
        return "";
    }


    /**
     * 是否需要pin和分享
     *
     * @param urlStr
     * @param key
     * @return
     */
    public static boolean isNeed(String urlStr, String key) {
        String needShareValue = getValue(urlStr, key);
        if (TextUtils.isEmpty(needShareValue)) {
            return false;
        }
        if ("no".equals(needShareValue)) {
            return false;
        }
        return true;
    }

    /**
     * 获取摇一摇url
     *
     * @param urls
     * @param key
     * @return
     */
    public static String getShakeValue(String urls, String key) {
        if (TextUtils.isEmpty(urls)) {
            return null;
        }
        int index = urls.indexOf("?");
        String[] sr1 = null;
        if (index > 0 && index < urls.length() - 1) {
            String str1 = urls.substring(index + 1);
            sr1 = str1.split("&");
        }

        if (sr1 == null || sr1.length == 0) {
            return null;
        }

        for (String str : sr1) {
            String[] tradeNum = str.split("=");
            if (tradeNum != null && tradeNum.length == 2) {
                if (tradeNum[0].equals(key)) {
                    return tradeNum[1];
                }
            }
        }
        return null;
    }
}
