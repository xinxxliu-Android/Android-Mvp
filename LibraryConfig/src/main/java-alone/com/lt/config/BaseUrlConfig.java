package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 网络请求BaseUrl
 * 单机版本 授权地址
 */
public class BaseUrlConfig {
    /**
     * 地址为 北京外网地址
     */
    public static String URL_DEBUG = "http://120.92.21.245";
    public static String URL_RELEASE = "http://120.92.21.245";
    public static String PORT = "8181";
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
