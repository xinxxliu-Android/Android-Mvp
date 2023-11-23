package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 网络请求BaseUrl
 */
public class BaseUrlConfig {
    //重庆开发环境地址 http://10.10.10.106:18080
    public static String URL_DEBUG = "http://10.10.10.106";
    public static String URL_RELEASE = "http://10.10.10.106";
    public static String PORT = "8000";
    //重庆测试环境http://10.10.10.21:31989
    public static String URL_DEBUG_TEST = "http://10.10.5.73";
    public static String URL_RELEASE_TEST = "http://10.10.5.73";
    public static String PORT_TEST = "18080";//31989

    /**
     * app服务
     */
    public static String APP_SERVER = "app-server";
    /**
     * 产品底座服务
     */
    public static String BLADE_DESK__SERVER = "blade-desk";
    //开发环境BASE_URL
    //public static String BASE_URL = (BuildConfig.DEBUG ? URL_DEBUG : URL_RELEASE) + ":" + PORT + "/";
    //测试环境 BASE_URL（目前就重庆有测试环境）
    public static String BASE_URL = UserSettingsConfig.getServerUrl((BuildConfig.DEBUG ? URL_DEBUG_TEST : URL_RELEASE_TEST) + ":" + PORT_TEST + "/");

    /**
     * Socket 地址
     */
    public static String SOCKET_URL_HOST = "ws://";
    public static String SOCKET_URL_SCHERE = "124.206.76.16";
    public static String SOCKET_URL_POST = "22080";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
