package com.lt.config;

import java.io.File;

/**
 * 网络请求BaseUrl
 */
public class BaseUrlConfig {
    /**
     * 清能院南京测试环境
     * http://10.10.1.119:8000
     */
    public static String URL_HTTP = "http://10.10.1.119";
    /**
     * 端口
     */
    public static String PORT = "8000";
    /**
     * app服务
     */
    public static String APP_SERVER = "api/app-server";
    /**
     * 产品底座服务
     */
    public static String BLADE_DESK__SERVER = "api/allcore-system";

    public static String BASE_URL = UserSettingsConfig.getServerUrl(URL_HTTP + ":" + PORT + File.separator);


    /**
     * Socket 地址
     */
    public static String SOCKET_URL_HOST = "ws://";
    public static String SOCKET_URL_SCHERE = "172.23.17.139";
    public static String SOCKET_URL_POST = "10081";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
