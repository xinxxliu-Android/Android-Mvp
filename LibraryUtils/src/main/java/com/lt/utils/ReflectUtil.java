package com.lt.utils;

import android.app.usage.StorageStatsManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused","unchecked"})
public final class ReflectUtil {
    /**
     * 获取类 class
     *
     * @param cls 完全限定类名
     * @return 类
     */
    public static Class<?> findClass(String cls) {
        try {
            Method forName = Class.class.getDeclaredMethod("forName", String.class);
            forName.setAccessible(true);
            Object invoke = forName.invoke(null, cls);
            return (Class<?>) invoke;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行函数
     *
     * @param t      被执行的类的对象
     * @param mtName 函数名
     * @param args   参数
     * @param <T>    泛型限定
     * @return 执行结果
     */
    public static <T> T invoke(T t, String mtName, Object... args) {
        try {
            Method method = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            method.setAccessible(true);
            method = (Method) method.invoke(t.getClass(), mtName, buildArgCls(args));
            if (method == null)
                return null;
            method.setAccessible(true);
            return (T) method.invoke(t, args);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 反射获取字段
     *
     * @param t      该类的对象
     * @param fName  字段名
     * @param fClass 字段类型 返回的数据类型
     * @return 值
     */
    public static <T, S> S get(T t, String fName, Class<S> fClass) {
        try {
            Method method = Class.class.getDeclaredMethod("getDeclaredField", String.class);
            method.setAccessible(true);
            Field f = (Field) method.invoke(t.getClass(), fName);
            if (f == null)
                return null;
            f.setAccessible(true);
            return (S) f.get(t);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取 类中的 静态字段的值
     *
     * @param tClass 要从该 类获取字段
     * @param fName  字段名
     * @param sClass 字段类型
     * @param <T>    类型
     * @param <S>    返回类型
     * @return x
     */
    public static <T, S> S get(Class<T> tClass, String fName, Class<S> sClass) {
        try {
            Method method = Class.class.getDeclaredMethod("getDeclaredField", String.class);
            method.setAccessible(true);
            Field f = (Field) method.invoke(tClass, fName);
            if (f == null || !Modifier.isStatic(f.getModifiers()))
                return null;
            f.setAccessible(true);
            return (S) f.get(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 反射 获取 当前对象中的 所有的字段值
     *
     * @param t 对象 不可为空
     * @return 当前对象中的 所有字段的字段值
     */
    @Nullable
    public static Map<String, Object> getFields(@NonNull Object t) {
        Method method;
        try {
            method = Class.class.getDeclaredMethod("getDeclaredFields");
            method.setAccessible(true);
            Field[] f = (Field[]) method.invoke(t.getClass());
            if (f == null || f.length == 0)
                return null;
            HashMap<String, Object> map = new HashMap<>();
            for (Field field : f) {
                map.put(field.getName(), get(t, field.getName(), Object.class));
            }
            return map;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射 获取当前类中的 所有字段 及 静态字段的值
     *
     * @param cls   x
     * @return      x
     */
    public static Map<String, Object> getClsFields(Class<?> cls) {
        Method method;
        try {
            method = Class.class.getDeclaredMethod("getDeclaredFields");
            method.setAccessible(true);
            Field[] f = (Field[]) method.invoke(cls);
            if (f == null || f.length == 0)
                return null;
            HashMap<String, Object> map = new HashMap<>();
            for (Field field : f) {
                if (Modifier.isStatic(field.getModifiers()))
                    map.put(field.getName(), get(null, field.getName(), Object.class));
                else
                    map.put(field.getName(), "Field is not static");
            }
            return map;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Class<?>[] buildArgCls(Object[] args) {
        if (args == null)
            return new Class[0];
        Class<?>[] classes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        return classes;
    }
}
