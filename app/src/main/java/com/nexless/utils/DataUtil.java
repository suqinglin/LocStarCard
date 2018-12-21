package com.nexless.utils;

/**
 * @date: 2018/12/19
 * @author: su qinglin
 * @description:
 */
public class DataUtil {

    public static CharSequence[] getSectorArray() {

        CharSequence[] sectorArr = new CharSequence[16];
        for (int i = 0; i < sectorArr.length; i++) {
            sectorArr[i] = "第" + (i + 1) + "扇区";
        }
        return sectorArr;
    }
}
