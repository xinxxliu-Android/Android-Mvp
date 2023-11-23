package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 北京 外网测试环境地址
 */
public class BaseUrlConfig {
    /**
     * 地址为 北京外网测试地址
     */
    /*public static String URL_DEBUG = "http://172.16.14.195";
    public static String URL_RELEASE = "http://172.16.14.195";*/

    /**
     * 北京现场环境
     */
    public static String URL_DEBUG = "http://111.198.150.30";
    public static String URL_RELEASE = "http://111.198.150.30";


    /**
     * 北京电科院内网地址
     */
//    public static String URL_DEBUG = "http://8.8.23.24";
//    public static String URL_RELEASE = "http://8.8.23.24";

    //北京电科院内网地址端口
//    public static String PORT = "10080";

    //public static String PORT = "18888";
    // 北京现场环境端口
    public static String PORT = "10081";
    public static String BASE_URL = UserSettingsConfig.getServerUrl((BuildConfig.DEBUG ? URL_DEBUG : URL_RELEASE) + ":" + PORT + "/");

    /**
     * Socket 地址 现场环境
     */
    public static String SOCKET_URL_HOST = "ws://";
    public static String SOCKET_URL_SCHERE = "111.198.150.30";
    public static String SOCKET_URL_POST = "10081";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
