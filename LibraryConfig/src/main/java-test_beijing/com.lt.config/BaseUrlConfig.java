package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 北京 外网测试环境地址
 */
public class BaseUrlConfig {
    /**
     * 地址为 北京内网测试地址
     */
//    public static String URL_DEBUG = "http://172.16.14.209";
//    public static String URL_RELEASE = "http://172.16.14.209";

    /**
     * 地址为 北京外网测试地址
     */
    public static String URL_DEBUG = "http://124.206.76.16";
    public static String URL_RELEASE = "http://124.206.76.16";

    /**
     * 北京开发环境
     */
//    public static String URL_DEBUG = "http://172.16.14.195";
//    public static String URL_RELEASE = "http://172.16.14.195";

    /**
     * 内网端口
     */
//    public static String PORT = "32080";
    /**
     * 外网端口
     */
    public static String PORT = "22080";
    /**
     * 北京开发环境端口
     */
//    public static String PORT = "18888";
    public static String BASE_URL = UserSettingsConfig.getServerUrl(BuildConfig.DEBUG ? URL_DEBUG : URL_RELEASE + ":" + PORT + "/");

    /**
     * Socket 地址
     */
    public static String SOCKET_URL_HOST = "ws://";
//    public static String SOCKET_URL_SCHERE = "172.16.14.195";
    public static String SOCKET_URL_SCHERE = "124.206.76.16";
//    public static String SOCKET_URL_POST = "18888";
    public static String SOCKET_URL_POST = "22080";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
