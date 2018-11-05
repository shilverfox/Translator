package com.translatmaster.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Calculator {
    private static MessageDigest _digest;

    public MD5Calculator() {
    }

    public static String calculateMD5(String s) {
        try {
            if (TextUtils.isEmpty(s)) {
                return "";
            }

            return calculateMD5(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return "";
    }

    public static synchronized String calculateMD5(byte[] input) {
        try {
            _digest = MessageDigest.getInstance("MD5");
            _digest.reset();
            _digest.update(input);
            byte[] hash = _digest.digest();
            return StringToolBox.getHexString(hash);
        } catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return "";
    }
}