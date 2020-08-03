package com.jbsx.utils.smskey;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 短信防刷验证码生成器
 *
 *
 * 签名字符串（算法：token,时间戳，随机数三个字符串按照首字母大小进行排序后拼接成字符串 ，hash(sha1(拼接的字符串)) ，加密后字符串转为大写）
 */
public class SmsKeyGenerator {

    static {
        System.loadLibrary("hello-jni");
    }

    public static void getKey(ISmsKeyGeneratorListener callback) {
        // 1.
        String randomInt = getRandomInt();
        String timeStamp = getTimeStamp();

        List<String> params = new ArrayList<>();
        params.add(getStringFromJNI());
        params.add(randomInt);
        params.add(timeStamp);

        Collections.sort(params);

        StringBuffer sb = new StringBuffer();
        for(String param : params) {
            sb.append(param);
        }

        // 2.
        String sha = "";
        try {
            sha = SHA.getSHA(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 3.
        String result = sha.toUpperCase();

        // 回调
        if (callback != null) {
            callback.onShaComplete(timeStamp, randomInt, result);
        }
    }

    private static String getRandomInt() {
        Random random = new Random();
        return random.nextInt() + "";
    }

    private static String getTimeStamp() {
        return System.currentTimeMillis() + "";
    }

    public native static String getStringFromJNI();
}
