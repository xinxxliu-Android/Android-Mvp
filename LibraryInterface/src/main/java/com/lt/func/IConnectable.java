package com.lt.func;

/**
 * 可连接的设备
 * 所有实现该接口的类，具有 连接功能
 * @param <T>
 */
public interface IConnectable<T> {
    void connected(T t);
}
