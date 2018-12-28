package com.nexless.locstarcard.Utils;

import android.util.Log;

/**
 * @date: 2018/12/18
 * @author: su qinglin
 * @description:
 */
public class Constants {

    public static boolean DEBUG = false;

    public static final byte[] DEFAULT_KEY_A = Utils.hexStringToBytes("FFFFFFFFFFFF"); // 默认密码A，如果刷了授权卡，客人卡将不再使用此密码
    public static final byte[] DEFAULT_KEY_B = Utils.hexStringToBytes("B4B4BCD1CBF8"); // 默认密码B
    public static final byte[] DEFAULT_CONTROL_WORD = Utils.hexStringToBytes("78778869"); // 控制字

    public static void logI(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }
}
