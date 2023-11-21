package com.lt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /*
     * 将时间戳转换为时间
     *
     * s就是时间戳
     */

    public static String stampToDate(long imestamp,String format){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        //如果它本来就是long类型的,则不用写这一步
//        long lt = new Long(imestamp);
        Date date = new Date(imestamp);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     * "yyyy-MM-dd HH:mm:ss"
     */
    public static long dateToStamp(String s,String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return ts;
    }


    public static String getWeek(long time) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(time));

        int year  = cd.get(Calendar.YEAR); //获取年份
        int month = cd.get(Calendar.MONTH); //获取月份
        int day   = cd.get(Calendar.DAY_OF_MONTH); //获取日期
        int week  = cd.get(Calendar.DAY_OF_WEEK); //获取星期

        String weekString;
        switch (week) {
            case Calendar.SUNDAY:
                weekString = "周日";
                break;
            case Calendar.MONDAY:
                weekString = "周一";
                break;
            case Calendar.TUESDAY:
                weekString = "周二";
                break;
            case Calendar.WEDNESDAY:
                weekString = "周三";
                break;
            case Calendar.THURSDAY:
                weekString = "周四";
                break;
            case Calendar.FRIDAY:
                weekString = "周五";
                break;
            default:
                weekString = "周六";
                break;

        }

        return weekString;
    }

}
