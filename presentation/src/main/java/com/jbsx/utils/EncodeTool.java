package com.jbsx.utils;

import android.text.TextUtils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncodeTool {

    /**
     * @param encrypted
     * @return AES加密算法解密
     * @throws Exception
     */
    public static String decrypt(String encrypted) {
        try {
            String seed = StatisticsReportUtil.getUUIDMD5();
            if (TextUtils.isEmpty(seed)) {
                return encrypted;
            }
            return decrypt(seed, encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    /**
     * 是否使用兼容android P的AES加密方式
     *
     * @return
     */
    private static boolean useNew() {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        return sdkVersion > 26;
    }

    /**
     * AES解密
     * 5.8
     *
     * @param encrypted
     * @return
     */
    public static String decryptByNewAES(String encrypted) {
        try {
            String seed = StatisticsReportUtil.getUUIDMD5();
            if (TextUtils.isEmpty(seed)) {
                return encrypted;
            }

            return AESUtilForAndroidP.decrypt(seed, encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    /**
     * AES加密
     * 5.8
     *
     * @param cleartext
     * @return
     */
    public static String encryptByNewAES(String cleartext) {
        try {
            String seed = StatisticsReportUtil.getUUIDMD5();
            if (TextUtils.isEmpty(seed)) {
                return cleartext;
            }

            return AESUtilForAndroidP.encrypt(seed, cleartext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cleartext;
    }

    /**
     * @param
     * @return AES加密算法加密
     * @throws Exception
     */
    public static String encrypt(String cleartext) {
        try {
            String seed = StatisticsReportUtil.getUUIDMD5();
            if (TextUtils.isEmpty(seed)) {
                return cleartext;
            }
            return encrypt(seed, cleartext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cleartext;
    }

    /**
     * @param
     * @return AES加密算法加密
     * @throws Exception
     */
    public static String encrypt(String seed, String cleartext) throws Exception {

        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);

    }

    /**
     * @param
     * @return AES加密算法加密
     * @throws Exception
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static String toHex(byte[] buf) {
        final String HEX = "0123456789ABCDEF";
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            result.append(HEX.charAt((buf[i] >> 4) & 0x0f)).append(
                    HEX.charAt(buf[i] & 0x0f));
        }
        return result.toString();
    }

    /**
     * @param encrypted
     * @return AES加密算法解密
     * @throws Exception
     */
    public static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }
}
