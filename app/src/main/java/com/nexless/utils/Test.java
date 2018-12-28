package com.nexless.utils;

/**
 * @date: 2018/12/20
 * @author: su qinglin
 * @description:
 */
public class Test {

    public static void main(String[] args) {
        byte b = 0x0C;
        int i = b & 0xFF;
        System.out.println(fillPosition(0x0C, 2, "0"));
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
}
