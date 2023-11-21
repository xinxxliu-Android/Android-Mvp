package com.lt.func;

/**
 * 辅助绑定view工具
 * @param <T>   当前辅助类 绑定的接口类型。原则上 禁止使用实体类型
 */
public interface IBaseHelper<T> {
    /**
     * 绑定
     * @param t 一般为当前接口对象  原则上 禁止使用实体类
     */
    void attach(T t);

    /**
     * 解绑 释放资源
     */
    void detach();
}
