package com.nexless.locstarcard.Utils;

/**
 * @date: 2018/12/17
 * @author: su qinglin
 * @description: 工具类
 */
public class Utils {
    /**
     * 数组转换成十六进制字符串
     *
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            String subStr = hexString.substring(i * 2, i * 2 + 2);
            d[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return d;
    }

    /**
     * 补位
     * @param src
     * @param length
     * @param fillStr
     * @return
     */
    public static String fillPosition(String src, int length, String fillStr) {

        int lenDiff = length - src.length();
        StringBuilder srcBuilder = new StringBuilder(src);
        for (int i = 0; i < lenDiff; i++) {
            srcBuilder.insert(0, fillStr);
        }
        return srcBuilder.toString();
    }

    /**
     * 补位
     * @param src
     * @param length
     * @param fillStr
     * @return
     */
    public static String fillPosition(Object src, int length, String fillStr) {

        String strString = String.valueOf(src);
        int lenDiff = length - strString.length();
        StringBuilder srcBuilder = new StringBuilder(strString);
        for (int i = 0; i < lenDiff; i++) {
            srcBuilder.insert(0, fillStr);
        }
        return srcBuilder.toString();
    }

    /**
     * 异或校验
     * @param datas 原数据
     * @return 校验结果
     */
    public static byte getXor(byte[] datas){

        byte temp=datas[0];

        for (int i = 1; i <datas.length; i++) {
            temp ^=datas[i];
        }

        return temp;
    }

}
