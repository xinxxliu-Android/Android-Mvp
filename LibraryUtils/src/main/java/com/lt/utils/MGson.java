package com.lt.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * 项目中 gson解析工具类
 * 所有gson相关，全部使用该类，避免重复创建对象造成资源额外开销
 */
public final class MGson {
    /**
     * 懒加载，使用时通过{@link #assertImpl()}确认生成gson对象
     * 全局静态变量，贯穿整个应用生命周期
     */
    private static Gson gson;

    /**
     * 解析
     * @param json  源
     * @param t     需要解析成的类型    可使用{@link TypeToken#getType()}获取带范型的对象
     *              如：  Type type = new TypeToken<MGson>() {}.getType();
     * @param <T>   返回类型
     * @return      r
     */
    public static <T> T fromJson(String json, Type t) {
        assertImpl();
        Type type = new TypeToken<MGson>() {
        }.getType();
        return gson.fromJson(json, t);
    }
    /**
     * 解析
     * @param json  源
     * @param t     需要解析成的类型
     * @param <T>   返回类型
     * @return      r
     */
    public static <T> T fromJson(String json, Class<T> t) {
        assertImpl();
        return gson.fromJson(json, t);
    }

    /**
     * 将对象转换为json字符串
     * @param obj   需要转换的对象
     * @return      json
     */
    public static String toJson(Object obj) {
        assertImpl();
        return gson.toJson(obj);
    }

    /**
     * 使用gson做其他用途
     * @return
     */
    public static Gson getGson() {
        assertImpl();
        return gson;
    }

    /**
     * 确认，唯一构建gson对象。
     */
    private synchronized static void assertImpl() {
        if (gson == null)
            gson = new Gson();
    }
}
