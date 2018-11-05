package com.translatmaster.utils;

import java.io.UnsupportedEncodingException;

public class StringToolBox {
    static final byte[] HEX_CHAR_TABLE = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    public StringToolBox() {
    }

    public static String getHexString(byte[] raw) {
        byte[] hex = new byte[2 * raw.length];
        int index = 0;
        byte[] var3 = raw;
        int var4 = raw.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            int v = b & 255;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 15];
        }

        String result = "";

        try {
            result = new String(hex, "ASCII");
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return result;
    }

    public static boolean isChinesePrefix(String word) {
        if (word != null && word.length() != 0) {
            char c = word.charAt(0);
            return 19968 <= c && c <= 171941;
        } else {
            return false;
        }
    }
}
