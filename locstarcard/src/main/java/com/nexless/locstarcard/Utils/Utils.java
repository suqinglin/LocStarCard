package com.nexless.locstarcard.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date: 2018/12/17
 * @author: su qinglin
 * @description: 工具类
 */
public class Utils {

    public static final String DATA_FORMAT_2 = "yyMMddHHmm";

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
     * 10位时间戳转为年月日 时分秒
     * @param time
     * @return
     */
    public static String longToString(long time)
    {
        long t = time*1000L;
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT_2);
        Date date = new Date(t);
        return sdf.format(date);
    }

    /**
     * 年月日时分秒转为10位时间戳
     * @param time
     * @return
     */
    public static long stringToLong(String time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_2);
        Date date = new Date();
        try{
            date = dateFormat.parse(time);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date.getTime()/1000;
    }

    /**
     * 时间毫秒数转byte[]
     * @return
     */
    public static byte[] timeToBytes(long time) {

        String timeStr = longToString(time);
        int length = timeStr.length() / 2;
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            String subStr = timeStr.substring(i * 2, i * 2 + 2);
            d[i] = (byte) Integer.parseInt(subStr);
        }
        return d;
    }

    /**
     * byte[]时间毫秒数
     * @return
     */
    public static long bytesToTime(byte[] timeArray) {

        StringBuilder sb = new StringBuilder();
        for (byte b: timeArray) {
            sb.append(fillPosition(b, 2, "0"));
        }
        return stringToLong(sb.toString());
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
