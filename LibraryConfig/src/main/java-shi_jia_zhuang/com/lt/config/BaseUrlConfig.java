package com.lt.config;

import java.io.File;

/**
 * 网络请求BaseUrl
 */
public class BaseUrlConfig {
    /**
     * 河北输电环境
     * https://172.23.17.139:10081
     * 河北配电环境
     * http://172.23.17.136:18000
     */
    /**
     * 输电
     */
    public static String URL_HTTPS = "https://172.23.17.139";
    /**
     * 输电端口
     */
    public static String PORTS = "10081";
    //配电
    //public static String URL_HTTP = "http://172.23.17.136";
    //配电测试环境
    public static String URL_HTTP = "http://10.10.1.123";
    //配电端口
    //public static String PORT = "18000";
    //配电测试端口
    public static String PORT = "8000";
    //app服务
    public static String APP_SERVER = "api/app-server";

    //产品底座服务
    public static String BLADE_DESK__SERVER = "api/blade-desk";

    public static String BASE_URL = UserSettingsConfig.getServerUrl(PluginConfig.isHttps() ? (URL_HTTPS + ":" + PORTS + File.separator) :
            (URL_HTTP + ":" + PORT + File.separator));

    /**
     * Socket 地址
     */
    public static String SOCKET_URL_HOST = "ws://";
    public static String SOCKET_URL_SCHERE = "172.23.17.139";
    public static String SOCKET_URL_POST = "10081";
    public static String SOCKET_URL_PATH = "appwebsocket";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH + "/";
}
