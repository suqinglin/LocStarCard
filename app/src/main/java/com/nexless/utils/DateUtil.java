package com.nexless.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date: 2018/12/20
 * @author: su qinglin
 * @description:
 */
public class DateUtil {

    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 10位时间戳转为年月日 时分秒
     * @param time
     * @return
     */
    public static String longToString(long time)
    {
        long t = time*1000L;
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
        Date date = new Date();
        try{
            date = dateFormat.parse(time);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date.getTime()/1000;
    }
}
