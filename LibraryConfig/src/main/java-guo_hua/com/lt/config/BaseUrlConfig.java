package com.lt.config;

import com.blankj.utilcode.BuildConfig;

import java.io.File;

/**
 * 网络请求BaseUrl
 */
public class BaseUrlConfig {
    // 国华现场环境：http://10.180.200.76:8000/
    // 国华南京测试环境：http://10.10.5.150:8000/

    public static String URL_DEBUG = "https://10.180.200.76";
    public static String URL_RELEASE = "https://10.180.200.76";
    /**
     * 服务
     */
    public static String URL_SERVER = "api/app-business-server";
    public static String PORT = "8000";
    //开发环境BASE_URL
    public static String BASE_URL = UserSettingsConfig.getServerUrl(BuildConfig.DEBUG ? URL_DEBUG : URL_RELEASE + ":" + PORT + File.separator);

    /**
     * 国华授权请求地址：
     */
    public static String SIGN_URL_RELEASE = "http://124.206.76.16";
    public static String SIGN_PORT = "28181";
    public static String SIGN_BASE_URL = SIGN_URL_RELEASE + ":" + SIGN_PORT + "/";

    /**
     * Socket 地址
     */
    public static String SOCKET_URL_HOST = "ws://";
    public static String SOCKET_URL_SCHERE = "124.206.76.16";
    public static String SOCKET_URL_POST = "22080";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
