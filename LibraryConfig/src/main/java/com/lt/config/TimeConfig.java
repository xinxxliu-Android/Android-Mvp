package com.lt.config;

/**
 * 时间配置
 * 项目中所有超时时间配置常量
 */
public class TimeConfig {
    /**
     * 网络请求 默认 读取超时时间
     */
    public static final long HTTP_READ = 60 * 1000;
    /**
     * 网络请求 默认 写出超时时间
     */
    public static final long HTTP_WRITE = 60 * 1000;
    /**
     * 网络请求 默认 呼叫超时时间
     */
    public static final long HTTP_CALL = 60000;
    /**
     * 飞机状态上传接口超时时间设置为4秒
     */
    public static final long HTTP_CALL_FLY_STATUS_TIME = 4000;
    /**
     * 网络请求 默认 连接超时时间
     */
    public static final long HTTP_CONNECT = 60 * 1000;

    /**
     * 以下四个字段 只用作图片上传
     */
    public static final long UPLOAD_READ = 60 * 1000;
    public static final long UPLOAD_WRITE = 60 * 1000;
    public static final long UPLOAD_CALL = 60 * 1000;
    public static final long UPLOAD_CONNECT = 60 * 1000;

    /**
     * 授权登陆超时时间 24小时
     */
    public static final long SIGN_TIME_OUT = 24 * 60 * 60 * 1000;
}
