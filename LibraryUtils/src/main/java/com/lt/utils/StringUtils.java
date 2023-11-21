package com.lt.utils;

import java.lang.reflect.Field;
import java.util.List;

public class StringUtils {

    public static String listStr(List<?> list, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Object o : list) {
                Field declaredField = o.getClass().getDeclaredField(str);
                declaredField.setAccessible(true);
                Object o1 = declaredField.get(o);
                sb.append(o1).append(";");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
