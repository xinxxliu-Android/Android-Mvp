package com.lt.config;

import java.io.File;

/**
 * 网络请求BaseUrl
 */
public class BaseUrlConfig {
    // 青海2.0系统正式环境地址：
    // 青海2.0系统测试环境地址：http://172.16.14.210:18080

    //IP地址
    public static String URL_HTTP = "http://172.16.14.210";

    //端口
    public static String PORT = "18080";

    //app服务-北京
    public static String APP_SERVER = "app-server";//qh20/
    //产品底座服务-北京
    public static String BLADE_DESK__SERVER = "allcore-system";//qh20/

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
