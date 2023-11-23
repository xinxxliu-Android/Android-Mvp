package com.lt.config;

/**
 * 状态吗标识 声明常亮类
 */
public interface CodeConfig {
    /**
     * 网络请求异常
     * 异常标识
     * 标志当前异常 是由网络请求时，服务器异常错误造成的
     */
    int NET_ERROR_SERVER = 233;
    /**
     * 网络请求异常
     * 异常标识
     * 标志当前异常 是由网络请求 path不对造成的
     */
    int NET_ERROR_PATH = 40;
    /**
     * 网络请求异常
     * 异常标识
     * 标志当前异常 是由json解析 无法解析造成的
     */
    int NET_ERROR_PARSE = 770;
    /**
     * 网络请求异常
     * 异常标识
     * 标志当前异常 是由网络原因 造成的 如 网络不稳定等
     */
    int NET_ERROR_NETWORK = 653;
    /**
     * 网络请求异常
     * 异常标识
     * 标志当前异常 是由 网络请求时，无法解析body造成的
     */
    int NET_ERROR_UNKNOWN = 661;
}
