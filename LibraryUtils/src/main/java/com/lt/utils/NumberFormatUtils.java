package com.lt.utils;

import java.math.BigDecimal;

/**
 * 数字 格式化显示工具类
 */
public final class NumberFormatUtils {
    /**
     * 格式化显示小数
     * @param in    保留几位小数
     * @param count 保留个数
     * @return  格式化后的小数
     */
    public static double format(double in,int count){
        String s = in + "";
        if (s.contains("NaN"))
            return 0;
        BigDecimal bigDecimal = new BigDecimal(in);
        BigDecimal bg = bigDecimal.setScale(count, BigDecimal.ROUND_UP);
        return bg.doubleValue();
    }

    public static String formatLength(double in,int lengthCount){
        StringBuilder temp = new StringBuilder();
        String s = in + "";
        int i = lengthCount - s.length();
        while (temp.length() <i) {
            temp.append("\u0020");
        }
        temp.append(in);
        return temp.toString();
    }
}
