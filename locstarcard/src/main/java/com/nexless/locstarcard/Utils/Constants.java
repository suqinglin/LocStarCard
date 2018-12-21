package com.nexless.locstarcard.Utils;

import android.util.Log;

/**
 * @date: 2018/12/18
 * @author: su qinglin
 * @description:
 */
public class Constants {

    public static boolean DEBUG = true;
    public static String SDK_VERSION = "1.0.0";
    public static final int STATUS_SUCC = 10000;
    public static final int STATUS_FAIL = 20000;
    public static final int STATUS_AUTH_FAIL = 20001;
    public static final int STATUS_NO_INIT = 20002;
    public static final int STATUS_NO_CARD_FOUND = 20003;
    public static final int STATUS_DATA_LOSE = 20004;
    public static final int STATUS_WRITE_FAIL = 20005;

    public static final byte[] DEFAULT_KEY_A = Utils.hexStringToBytes("FFFFFFFFFFFF"); // 默认密码A，如果刷了授权卡，客人卡将不再使用此密码
    public static final byte[] DEFAULT_KEY_B = Utils.hexStringToBytes("B4B4BCD1CBF8"); // 默认密码B
    public static final byte[] DEFAULT_CONTROL_WORD = Utils.hexStringToBytes("78778869"); // 控制字
    public static final byte[] DEFAULT_CONSUMER_ANTH = Utils.hexStringToBytes("0123456789AB"); // 默认客人授权，用于设置授权卡

    public static void logI(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }
}
