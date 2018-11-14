package com.jbsx.view.login.loginsdk.model;

/**
 * 定义登录sdk返回的错误码信息，如果需要更换sdk，只需要修改该类中具体的变量值即可
 * Created by lijian15 on 2017/11/9.
 */

public class AppReplyCode {
    public static final byte reply0x0 = 0;
    public static final byte reply0x1 = 1;
    public static final byte reply0x2 = 2;
    public static final byte reply0x3 = 3;
    public static final byte reply0x4 = 4;
    public static final byte reply0x5 = 5;
    public static final byte reply0x6 = 6;

    /** 账号不存在 */
    public static final byte reply0x7 = 7;
    public static final byte reply0x8 = 8;
    public static final byte reply0x9 = 9;
    public static final byte reply0xa = 10;

    /** bcde, token问题 */
    public static final byte reply0xb = 11;
    public static final byte reply0xc = 12;
    public static final byte reply0xd = 13;
    public static final byte reply0xe = 14;

    public static final byte reply0xf = 15;
    public static final byte reply0x10 = 16;
    public static final byte reply0x11 = 17;
    public static final byte reply0x12 = 18;
    public static final byte reply0xfe = -2;
    public static final byte reply0xff = -1;
    public static final byte reply0x16 = 22;
    public static final byte reply0x17 = 23;
    public static final byte reply0x18 = 24;
    public static final byte reply0x19 = 25;
    public static final byte reply0x20 = 32;
    public static final byte reply0x21 = 33;
    public static final byte reply0x22 = 34;

    /** 23-25:走微信账号绑定流程 */
    public static final byte reply0x23 = 35;
    public static final byte reply0x24 = 36;
    public static final byte reply0x25 = 37;

    public static final byte reply0x26 = 38;
    public static final byte reply0x27 = 39;
    public static final byte reply0x28 = 40;
    public static final byte reply0x29 = 41;
    public static final byte reply0x35 = 53;
    public static final byte reply0x36 = 54;
    public static final byte reply0x37 = 55;
    public static final byte reply0x38 = 56;
    public static final byte reply0x39 = 57;
    public static final byte reply0x4f = 79;

    /** 80-8f: 风控情况 */
    public static final byte reply0x80 = -128;
    public static final byte reply0x81 = -127;
    public static final byte reply0x82 = -126;
    public static final byte reply0x83 = -125;
    public static final byte reply0x84 = -124;
    public static final byte reply0x85 = -123;
    public static final byte reply0x86 = -122;
    public static final byte reply0x87 = -121;
    public static final byte reply0x88 = -120;
    public static final byte reply0x89 = -119;
    public static final byte reply0x8a = -118;
    public static final byte reply0x8b = -117;
    public static final byte reply0x8c = -116;
    public static final byte reply0x8d = -115;
    public static final byte reply0x8e = -114;
    public static final byte reply0x8f = -113;

    public static final byte replyJumpNumMin = -128;
    public static final byte replyJumpNumMax = -113;
    public static final byte reply0x64 = 100;
    public static final byte reply0x67 = 103;
    public static final byte reply0x68 = 104;
    public static final byte reply0x1a = 26;
    public static final byte reply0x1b = 27;
    public static final byte reply0x1c = 28;
    public static final byte reply0x1d = 29;
    public static final byte reply0x1e = 30;
    public static final byte reply0x1f = 31;
    public static final byte reply0x13 = 19;
    public static final byte reply0x14 = 20;
    public static final byte reply0x15 = 21;
    public static final byte reply0x32 = 50;
    public static final byte reply0x34 = 52;
    public static final byte reply0x77 = 119;
    public static final byte reply0x7a = 122;
    public static final byte reply0x7b = 123;
    public static final byte reply0x7e = 126;
    public static final byte reply0x6a = 106;
    public static final byte reply0xa0 = -96;
    public static final byte reply0xa5 = -91;
    public static final byte reply0xa6 = -90;
    public static final byte reply0xb1 = -79;
    public static final byte reply0xac = -84;
    public static final byte reply0xad = -83;
    public static final byte reply0xae = -82;
    public static final byte reply0xaf = -81;
    public static final byte reply0xb2 = -78;
    public static final byte reply0x78 = 120;

    /** 需要设置密码 */
    public static final byte reply0x31 = 49;
}
