package com.lt.func;

/**
 * 单接口回调，方便使用lambda表达式
 * 全应用统一使用
 * 唯一回调 {@link #callback(T)}
 *
 * @param <T>
 */
public interface ICallBack<T> {
    void callback(T t);
}
