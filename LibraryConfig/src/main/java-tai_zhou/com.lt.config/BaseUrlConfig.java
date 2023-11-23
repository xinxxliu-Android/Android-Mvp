package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 网络请求BaseUrl
 * 单机版本 授权地址
 */
public class BaseUrlConfig {
    // 泰州测试环境地址：10.10.10.141:
//    public static String URL_DEBUG = "http://10.10.10.141";
//    public static String URL_RELEASE = "http://10.10.10.141";

    // 泰州内网测试环境地址：2.0.0.1:30070
//    public static String URL_DEBUG = "http://2.0.0.1";
//    public static String URL_RELEASE = "http://2.0.0.1";
//    public static String PORT = "30070";

    // 泰州测试环境地址(南京)：10.10.5.140:
    public static String URL_DEBUG = "http://10.10.5.140";
    public static String URL_RELEASE = "http://10.10.5.140";
    public static String PORT = "8000";

    public static String BASE_URL = UserSettingsConfig.getServerUrl(BuildConfig.DEBUG ? URL_DEBUG : URL_RELEASE + ":" + PORT + "/");


    /**
     * app服务
     */
    public static String APP_SERVER = "api/app-server";

    /**
     * 产品底座服务
     */
    public static String BLADE_DESK__SERVER = "api/blade-desk";
    /**
     * Socket 地址
     */
    public static String SOCKET_URL_HOST = "ws://";
    public static String SOCKET_URL_SCHERE = "124.206.76.16";
    public static String SOCKET_URL_POST = "22080";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
