package com.jbsx.utils.sha1;

/**
 * 对外提供getSHA(String str)方法
 *
 * @author randyjia
 *
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
    public static String getSHA(String val) throws NoSuchAlgorithmException{
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.update(val.getBytes());
        byte[] m = sha1.digest();//加密
        return byte2hex(m);
    }

    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }
}
