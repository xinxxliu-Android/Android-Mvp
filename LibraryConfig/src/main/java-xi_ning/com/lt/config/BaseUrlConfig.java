package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 网络请求BaseUrl
 */
public class BaseUrlConfig {
    /**
     * 测试
     * 218.2.219.130:18000
     * 正式
     * 10.180.200.76:8000
     */
    public static String URL_DEBUG = "http://124.206.76.16";
    public static String URL_RELEASE = "http://218.2.219.130";
    public static String PORT = "22080";
    public static String BASE_URL = UserSettingsConfig.getServerUrl((BuildConfig.DEBUG ? URL_DEBUG : URL_RELEASE) + ":" + PORT + "/");

    /**
     * Socket 地址
     */
    public static String SOCKET_URL_HOST = "ws://";
    public static String SOCKET_URL_SCHERE = "124.206.76.16";
    public static String SOCKET_URL_POST = "22080";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
